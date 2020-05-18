package cn.thens.kdroid.sample.launcher

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.thens.kdroid.vadapter.VAdapter
import cn.thens.kdroid.vadapter.VRecyclerAdapter
import cn.thens.kdroid.vadapter.toHolder

class AppItemAdapter : VRecyclerAdapter<ResolveInfo>() {
    override fun createHolder(viewGroup: ViewGroup, itemType: Int): VAdapter.Holder<ResolveInfo> {
        val context = viewGroup.context
        lateinit var vIcon: ImageView
        lateinit var vTitle: TextView
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)

            ImageView(context).apply {
                vIcon = this
                layoutParams = ViewGroup.LayoutParams(48, 48).apply {
                    gravity = Gravity.CENTER
                }
            }.also { addView(it) }

            TextView(context).apply {
                vTitle = this
                setPadding(2, 2, 2, 2)
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
            }.also { addView(it) }
        }.toHolder { data, _ ->
            val activityInfo = data.activityInfo
            val packageManager = context.packageManager
            vIcon.setImageDrawable(activityInfo.loadIcon(packageManager))
            vTitle.text = data.loadLabel(packageManager)
            setOnClickListener {
                val component = ComponentName(activityInfo.packageName, activityInfo.name)
                context.startActivity(Intent().setComponent(component))
            }
        }
    }

    companion object {
        fun getAppList(context: Context): List<ResolveInfo> {
            return context.packageManager.queryIntentActivities(Intent().setAction(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), 0)
        }
    }
}