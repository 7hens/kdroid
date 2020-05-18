package cn.thens.kdroid.sample.vadapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import cn.thens.kdroid.sample.R
import cn.thens.kdroid.sample.test.YaData
import cn.thens.kdroid.vadapter.VAdapter
import cn.thens.kdroid.vadapter.inflate
import kotlinx.android.synthetic.main.item_user.view.*
import kotlinx.android.synthetic.main.item_ya.view.*
import kotlinx.android.synthetic.main.item_user.view.vDescription as vUserDescription

class MultiItemTypeAdapter : BaseSampleAdapter<YaData>() {
    override fun createDataList(): List<YaData> {
        TODO()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun createHolder(viewGroup: ViewGroup, itemType: Int): VAdapter.Holder<YaData> {
        return if (get(itemType).isUser) {
            object : VAdapter.Holder<YaData> {
                override val view: View = viewGroup.inflate(R.layout.item_user)

                override fun bind(data: YaData, position: Int) {
                    Log.e("@", "===inject($position)")
                    view.vUserName.text = data.title
                    view.vUserDescription.text = "$position. ${data.description}"
                }
            }
        } else {
            object : VAdapter.Holder<YaData> {
                override val view: View = viewGroup.inflate(R.layout.item_ya)

                override fun bind(data: YaData, position: Int) {
                    Log.e("@", "===inject($position)")
                    view.vTitle.text = data.title
                    view.vDescription.text = "$position. ${data.description}"
                }
            }
        }
    }
}