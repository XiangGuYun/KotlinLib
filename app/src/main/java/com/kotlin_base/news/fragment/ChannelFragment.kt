package com.kotlin_base.news.fragment

import android.annotation.SuppressLint
import android.view.View
import com.kotlin_base.R
import com.kotlin_base.news.NewsActivity
import com.kotlinlib.other.LayoutId
import com.kotlinlib.view.KotlinFragment
import com.kotlinlib.view.RVUtils
import kotlinx.android.synthetic.main.fragment_channel.*
import org.greenrobot.eventbus.EventBus

@SuppressLint("ValidFragment")
@LayoutId(R.layout.fragment_channel)
class ChannelFragment(title: String): KotlinFragment() {

    val typeTitle = title

    override fun init(view: View?) {

    }

    override fun onResume() {
        super.onResume()
        if(typeTitle=="推荐频道"){
            RVUtils(rvChannelList).rvAdapter(NewsActivity.rcList,{
                holder, pos ->
                holder.setText(R.id.tvChannelName,NewsActivity.rcList[pos])
                holder.setOnClickListener(R.id.tvAddChannel) {
                    EventBus.getDefault().post(Pair(4,NewsActivity.rcList[pos]))
                    NewsActivity.rcList.remove(NewsActivity.rcList[pos])
                    rvChannelList.update()
                }
            },R.layout.item_rv_channel_list)

            rvChannelList.viewTreeObserver.addOnGlobalLayoutListener {
                "height is ${rvChannelList.height}".logD("Size")
                EventBus.getDefault().post(Pair(3,rvChannelList.height))
            }

        }

    }
}