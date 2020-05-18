package cn.thens.kdroid.sample.vadapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import cn.thens.kdroid.vadapter.VAdapter
import cn.thens.kdroid.vadapter.toHolder

class CarouselAdapter : BaseSampleAdapter<String>() {
    override fun createDataList(): List<String> {
        return listOf("http://www.maniacworld.com/super-dog.jpg", "http://www.akajanerandom.com/wp-content/uploads/2013/01/shaggy_dog-16.jpg", "http://harboranimalhospital.com/files/2015/03/smiling-dog.jpg")
    }

    override fun createHolder(viewGroup: ViewGroup, itemType: Int): VAdapter.Holder<String> {
        val context = viewGroup.context
        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
        return ImageView(context).apply {
            layoutParams = RecyclerView.LayoutParams(matchParent, 360)
        }.toHolder { _, _ ->

        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }
}