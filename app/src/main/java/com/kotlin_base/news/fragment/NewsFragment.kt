package com.kotlin_base.news.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import com.kotlin_base.R
import com.kotlin_base.news.NewsActivity
import com.kotlin_base.news.bean.News
import com.kotlin_base.news.net.NetUtils
import com.kotlinlib.other.LayoutId
import com.kotlinlib.view.KotlinFragment
import com.kotlinlib.view.RVUtils
import kotlinx.android.synthetic.main.fragment_new.*
import org.greenrobot.eventbus.EventBus

@SuppressLint("ValidFragment")
@LayoutId(R.layout.fragment_new)
class NewsFragment(kw: String) : KotlinFragment() {

    val keyword = kw
    var currentPage = 1
    var hasNext = true
    var datas = ArrayList<News.DataBean>()
    var isFirstReqData:Boolean = true
    lateinit var newsRVUtils: RVUtils

    override fun init(view: View?) {
    }

    override fun onResume() {
        super.onResume()
        //对于UI的操作一定要放到onResume中，因为此时Fragment是可见的
        refreshLayout.setOnRefreshListener{
            reqData(true,false)
        }
        refreshLayout.setOnLoadMoreListener {
            if(!hasNext){
                "没有更多数据了".toast()
                return@setOnLoadMoreListener
            }
            currentPage++
            reqData(false,true)
        }
    }

    /**
     * 首次请求数据
     */
    fun reqFirstData(){
        if(isFirstReqData){
            reqData(false,false)
            isFirstReqData = false
        }
    }
    
    fun getAct(): NewsActivity {
        return activity as NewsActivity
    }

    /**
     * 请求数据
     */
    private fun reqData(isRefresh:Boolean,isLoadMore:Boolean) {
        if(isFirstReqData) EventBus.getDefault().post(Pair(1,0))
        NetUtils<News>().get(News::class.java,keyword,currentPage,{
            hasNext = it.isHasNext
            if(!isRefresh&&!isLoadMore){//初始化适配器
                EventBus.getDefault().post(Pair(2,0))
                initRVUtils(it.data)
            }else if(isRefresh){//下拉刷新
                datas.clear()
                datas.addAll(it.data)
                rvNews.update()
                "刷新完成".toast()
                refreshLayout.finishRefresh()
            }else{//加载更多
                datas.addAll(ArrayList(it.data))
                rvNews.update()
                refreshLayout.finishLoadMore()
            }
        },{//发生错误时
            EventBus.getDefault().post(Pair(2,0))
            it.toast()
            if(isRefresh) refreshLayout.finishRefresh()
            if(isLoadMore) refreshLayout.finishLoadMore()
        })

    }

    private fun initRVUtils(origin: List<News.DataBean>) {
        datas = ArrayList(origin)
        newsRVUtils = RVUtils(rvNews).rvMultiAdapter(datas,{
            holder, pos ->
            holder.setText(R.id.tvTitle,datas[pos].title)
            holder.setText(R.id.tvSource,datas[pos].posterScreenName)
            holder.setText(R.id.tvPublishTime,(datas[pos].publishDate).toString().fmtDate("yyyy-MM-dd"))
            if(datas[pos].imageUrls!=null){
                when(datas[pos].imageUrls.size){
                    1->{
                        val iv1 = holder.getView<ImageView>(R.id.iv1)
                        getAct().showBmp(datas[pos].imageUrls[0],iv1)
                    }
                    2->{
                        val ivList = listOf(holder.getView<ImageView>(R.id.iv1), holder.getView(R.id.iv2))
                        for ((i,item) in ivList.withIndex()){
                            getAct().showBmp(datas[pos].imageUrls[i],item)
                        }
                    }
                    else->{
                        val ivList = listOf(holder.getView<ImageView>(R.id.iv1),
                                holder.getView(R.id.iv2),
                                holder.getView(R.id.iv3))
                        for ((i,item) in ivList.withIndex()){
                            getAct().showBmp(datas[pos].imageUrls[i],item)
                        }
                    }
                }
            }
        },{
            if(datas[it].imageUrls==null)
                0
            else{
                when(datas[it].imageUrls.size){
                    1->1
                    2->2
                    else->3
                }
            }
        },R.layout.item_news,R.layout.item_news1,R.layout.item_news2,R.layout.item_news3)
    }

    override fun onDestroy() {
        super.onDestroy()
        "fragment销毁了$keyword".logD()
    }

}