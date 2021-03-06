package com.kotlinlib

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlinlib.other.BaseInterface
import com.kotlinlib.other.LayoutId
import com.kotlinlib.utils.DensityUtils

abstract class KotlinActivity : AppCompatActivity(), BaseInterface {

    val ACTIVITY_NAME = "ac_name"
    var gson = Gson()

    companion object {
        var actList = ArrayList<Activity>()
        var currAct:String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewInject = this::class.annotations[0] as LayoutId
        setContentView(viewInject.id)
        "当前的Activity是${this.javaClass.simpleName}".logD(ACTIVITY_NAME)
        init(savedInstanceState)
        actList.add(this)
    }

    override fun onResume() {
        super.onResume()
        currAct = javaClass.simpleName
    }

    override fun onDestroy() {
        actList.remove(this)
        super.onDestroy()
    }

    protected abstract fun init(bundle: Bundle?)

    /**
     * 获取对象JSON字符串
     * @param any Any
     * @return String
     */
    fun jsonStr(any: Any):String{
        return gson.toJson(any)
    }

    /**
     * 土司提示
     * @param isLong 是否显示更长时间
     */
    fun Any.toast(isLong: Boolean=false){
        if(isLong)
            Toast.makeText(this@KotlinActivity,this.toString(),
                    Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this@KotlinActivity,this.toString(),
                    Toast.LENGTH_LONG).show()
    }

    /**
     * 尺寸单位转换
     */
    fun Number.px2dp():Int{
        return DensityUtils.px2dip(this@KotlinActivity, this.toFloat())
    }

    fun Number.dp2px():Int{
        return DensityUtils.dip2px(this@KotlinActivity, this.toFloat())
    }

    fun Number.px2sp():Int{
        return DensityUtils.px2sp(this@KotlinActivity, this.toFloat())
    }

    fun Number.sp2px():Int{
        return DensityUtils.sp2px(this@KotlinActivity, this.toFloat())
    }

    inner class JsonList<T>{
        fun transList(jsonStr:String): List<T> {
            return gson.fromJson(jsonStr, object : TypeToken<List<T>>(){}.type) as List<T>
        }

        fun transArrayList(jsonStr:String): ArrayList<T> {
            return gson.fromJson(jsonStr, object : TypeToken<ArrayList<T>>(){}.type) as ArrayList<T>
        }
    }

}