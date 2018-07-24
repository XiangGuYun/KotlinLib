package com.kotlin_base.demo

import android.graphics.Color
import android.os.Bundle
import com.kotlin_base.R
import com.kotlinlib.KotlinActivity
import com.kotlinlib.other.LayoutId
import com.kotlinlib.other.Group
import com.kotlinlib.view.PopupUtils
import kotlinx.android.synthetic.main.activity_main.*

@LayoutId(R.layout.activity_main)
class MainActivity : KotlinActivity() {


    override fun init(bundle: Bundle?) {
        //Activity跳转简化
        btnTest.click {go(SecondActivity::class.java,"name" to "lelei", "age" to 12, "isMale" to false)}

        //快速将数组转换为用某一分隔符分隔的字符串
        btnTest1.click {tvTest1.text= appendStr((1..100).toList(),","){(1..100).toList()[it].toString()}}

        //弹出窗口
        val popup = PopupUtils(this, R.layout.window,140.dp2px() to 60.dp2px(), {"消失了".toast()})
        btnTest3.click {popup.window.showAsDropDown(btnTest)}

        //将多个同一类型的单位组合化，并设置属性
        Group(tvTest4_1,tvTest4_2,tvTest4_3).text("hello","back","66666").textColor(Color.RED,Color.GREEN,Color.BLUE)

        //三目运算
        val testVal = if(Math.random()>0.5) "666" else "233"

        //本地存储操作
        putSP("username","admin")
        getSP("username","")

        //JSON数组字符串转数组
        val jsonStr = "['name','age','sex']"
        val listStr = JsonList<String>().transList(jsonStr)
        btnTestJSONArray.click {
            (appendStr(listStr, ",") { listStr[it] }).toast()
        }

        //列表
        btnRV.click {
            go(RVActivity::class.java)
        }

    }

}
