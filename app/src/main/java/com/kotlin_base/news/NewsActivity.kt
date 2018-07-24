package com.kotlin_base.news

import com.kotlinlib.other.TV
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.kotlin_base.R
import com.kotlin_base.news.fragment.ChannelFragment
import com.kotlin_base.news.fragment.NewsFragment
import com.kotlinlib.KotlinActivity
import com.kotlinlib.other.LayoutId
import com.kotlinlib.utils.OnPageChange
import com.kotlinlib.view.FragPagerUtils
import com.kotlinlib.view.RVUtils
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.menu_right.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

@LayoutId(R.layout.activity_news)
class NewsActivity : KotlinActivity() {

    val channelSelectList = arrayListOf("推荐频道","精彩栏目","地方频道")
    val ORDERED_CHANNEL="订阅频道"
    val RECOMMEND_CHANNEL="推荐频道"
    var isOpen = false//是否打开侧滑菜单
    val MAIN_COLOR = "#BCEE68".color()

    companion object {
        var kwList:ArrayList<String> = arrayListOf("杭州","新闻","娱乐","科技",
                "军事","游戏")//当前的频道列表
        var rcList = arrayListOf("星座","证券","足球","NBA","汽车","电影",
                "评论","电视","文化","明星","情感")//待添加列表
    }

    @Subscribe
    fun handleEvent(pair:Pair<Int,Any>){
        when(pair.first){
            1->{
                ring.show()
                ring.startAnim()
            }
            2->{
                ring.stopAnim()
                ring.hide()
            }
            3->{//动态配置ViewPager高度
                val params =  LinearLayout.LayoutParams(MP,pair.second as Int)
                params.setMargins(10.dp2px(),10.dp2px(),10.dp2px(),10.dp2px())
                vpContainer.layoutParams =params
            }
            4->{//更新频道列表，TabLayout和ViewPager
                kwList.add(pair.second.toString())
                rcList.remove(pair.second.toString())
                putSP(ORDERED_CHANNEL,gson.toJson(kwList))
                putSP(RECOMMEND_CHANNEL,gson.toJson(rcList))
                gson.toJson(rcList).logD()
                fpUtils.fragments.add(NewsFragment(pair.second.toString()))
                fpUtils.adapter.notifyDataSetChanged()
                rvChannel.update()
                for ((i,it) in kwList.withIndex()){
                    tlNew.getTabAt(i)?.text = it
                }
                vpNews.currentItem = kwList.size-1
            }
        }
    }

    override fun onBackPressed() {
        if(isOpen){
            drawer.closeDrawer(Gravity.RIGHT)
            isOpen=false
        }else{
            finish()
        }
    }

    private lateinit var fpUtils: FragPagerUtils<NewsFragment>

    private lateinit var fragments: List<NewsFragment>

    override fun init(bundle: Bundle?) {
        EventBus.getDefault().register(this)
        initView()
        initData()
    }

    private fun initData() {
        //如果没有初始数据，那就放入初始数据
        if(!getSP(RECOMMEND_CHANNEL,"").toString().isEmpty()){
            kwList = JsonList<String>().transArrayList(getSP(ORDERED_CHANNEL,"").toString())
            rcList = JsonList<String>().transArrayList(getSP(RECOMMEND_CHANNEL,"").toString())
        }
        fragments = kwList.map { NewsFragment(it) }
        val channelFragments = ArrayList(channelSelectList.map { ChannelFragment(it) })
        fpUtils = FragPagerUtils(this,vpNews,fragments)
        fpUtils.addTabLayout(tlNew,true,true) { tab, index ->
            tab.text = kwList[index]
        }
        //暂且只请求第一个fragment的初始数据
        fragments[0].reqFirstData()
        //监听ViewPager的翻页事件
        vpNews.listenPageChange(object : OnPageChange {
            override fun onPageSelected(position: Int) {
                //只有当其它页面被首次选中时，才去请求这个页面初始数据
                fragments[position].reqFirstData()
            }
        })
        //点击编辑打开侧滑菜单
        initDrawer(drawer,Gravity.RIGHT)
        tvEdit.click {
            openDrawer(drawer,Gravity.RIGHT)
            isOpen=true
        }
        //回退关闭侧滑菜单
        tvBack.click {
            drawer.closeDrawer(Gravity.RIGHT)
            isOpen=false
        }
        //设置已选频道列表
        RVUtils(rvChannel).gridManager(4,true)
                .rvMultiAdapter(kwList,{
                    holder, pos ->
                    holder.setText(R.id.tvChannelCell,kwList[pos])
                    if(rvChannel.tag==null){
                        holder.getView<TV>(R.id.tvChannelCell).setNullTVDrawable()
                        holder.setOnClickListener(R.id.tvChannelCell,null)
                    }else{
                        holder.getView<TV>(R.id.tvChannelCell).setRightTVDrawable(R.mipmap.delete)
                        //删除已选频道
                        holder.setOnClickListener(R.id.tvChannelCell) {
                            fpUtils.fragments.forEach {
                                if(it.keyword==kwList[pos]){
                                    fpUtils.fragments.remove(it)
                                    return@forEach
                                }
                            }
                            kwList.remove(kwList[pos])
                            rcList.add(kwList[pos])
                            putSP(ORDERED_CHANNEL,gson.toJson(kwList))
                            putSP(RECOMMEND_CHANNEL,gson.toJson(rcList))
                            fpUtils.adapter.notifyDataSetChanged()
                            rvChannel.update()
                        }
                    }
                },{
                    when((it + 1)%4){
                        1->0
                        0->2
                        else->1
                    }
                },R.layout.item_channel_left,R.layout.item_channel_center,R.layout.item_channel_right)
        //备选频道翻页
        FragPagerUtils<ChannelFragment>(this,vpContainer,channelFragments)
                .addTabLayout(tlChannel,true){ tab, index ->
                    tab.text = channelSelectList[index]
                }
        //点击编辑
        tvEditChannel.click1 {
            when((it as TextView).text){
                "编辑"->{
                    tvTip.show()
                    rvChannel.tag="1"
                    rvChannel.update()
                    it.text="完成"
                }
                "完成"->{
                    tvTip.hide()
                    rvChannel.tag=null
                    rvChannel.update()
                    it.text="编辑"
                }
            }
        }
    }

    private fun initView() {
        ring.setViewColor(MAIN_COLOR)
        tlNew.setSelectedTabIndicatorColor(MAIN_COLOR)
    }

}
