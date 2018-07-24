package com.kotlin_base.demo

import android.os.Bundle
import android.view.View
import com.kotlin_base.R
import com.kotlinlib.KotlinActivity
import com.kotlinlib.other.LayoutId
import com.kotlinlib.view.RVUtils
import kotlinx.android.synthetic.main.activity_rv.*

@LayoutId(R.layout.activity_rv)
class RVActivity : KotlinActivity() {

    val list = ArrayList((1..10).toList().map{"Item$it"})//data


    override fun init(bundle: Bundle?) {


        val rvUtils = RVUtils(rv).rvMultiAdapter(list, { holder, pos ->
            holder.setText(R.id.tvCell,list[pos])
            holder.setText(R.id.tvCell1,list[pos])
            holder.setImageResource(R.id.ivCell, R.mipmap.ic_launcher)
            holder.getView<View>(R.id.cell).click { (pos+1).toast() }
        },{
            when(it){//针对不同的列表项位置来返回不同的布局
                0->0
                list.size-1->2
                else->1
            }
        }, R.layout.item_head, R.layout.item_body, R.layout.item_foot)

        rvUtils.pageConfig(1,10)//设置当前页和总页数
        .enableRefresh(refresh, {//下拉刷新
            monitorReqData {
                data:List<String>->
                rvUtils.refresh(data){
                    rvUtils.pageConfig(1,10)
                    "刷新成功".toast()
                }
            }
        },{//加载更多
            rvUtils.checkMore({
                "没有更多数据了".toast()
            },{page:Int->
                monitorReqData {
                    data:List<String>->
                    rvUtils.addMore(data){
                        rvUtils.pageConfig(1,10)
                        "加载完成".toast()
                    }
                }
            })
        })

    }

    fun monitorReqData(get:(List<String>)->Unit) {
        get.invoke(listOf("1","2","3"))
    }

}
