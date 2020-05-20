package cn.thens.kdroid.vadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface VAdapter {
    val itemCount: Int

    fun getItemType(position: Int): Int = 0

    fun createHolder(container: ViewGroup, itemType: Int): Holder

    fun bindHolder(holder: Holder, position: Int) {
        holder.bind(this, position)
    }

    interface Holder {
        val view: View

        fun bind(adapter: VAdapter, position: Int)
    }

    fun helper(): VAdapterHelper {
        return if (this is VAdapterHelper) this else VAdapterHelper(this)
    }

    fun pager(): VPagerAdapter {
        return VPagerAdapter(helper())
    }

    fun list(): VListAdapter {
        return VListAdapter(helper())
    }

    fun recycler(): VRecyclerAdapter {
        return VRecyclerAdapter(this)
    }

    fun mergeWith(adapter: VAdapter): VAdapter {
        return merge(this, adapter)
    }

    fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    companion object {
        inline fun <V : View> holder(itemView: V, crossinline bind: V.(position: Int) -> Unit): Holder {
            return object : Holder {
                override val view: View = itemView

                override fun bind(adapter: VAdapter, position: Int) {
                    itemView.bind(position)
                }
            }
        }

        fun from(creators: List<(container: ViewGroup) -> Holder>): VAdapter {
            return object : VAdapter {
                override val itemCount: Int get() = creators.size

                override fun getItemType(position: Int): Int {
                    return position
                }

                override fun createHolder(container: ViewGroup, itemType: Int): Holder {
                    return creators[itemType](container)
                }
            }
        }

        fun merge(vararg adapters: VAdapter): VAdapter {
            return object : VAdapter {
                override val itemCount: Int
                    get() = adapters.fold(0) { acc, v -> acc + v.itemCount }

                override fun getItemType(position: Int): Int {
                    return position
                }

                override fun createHolder(container: ViewGroup, itemType: Int): Holder {
                    var position = itemType
                    adapters.forEach { adapter ->
                        if (position < adapter.itemCount) {
                            return adapter.createHolder(container, adapter.getItemType(position))
                        }
                        position -= adapter.itemCount
                    }
                    throw IndexOutOfBoundsException("expect $itemType, actual $itemCount")
                }
            }
        }
    }
}

