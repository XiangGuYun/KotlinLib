package com.zoga.yun.kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlindemo.base.utils.DensityUtils.Companion.dip2px
import com.kotlindemo.base.utils.DensityUtils.Companion.px2dip
import com.kotlindemo.base.utils.DensityUtils.Companion.px2sp
import com.kotlindemo.base.utils.DensityUtils.Companion.sp2px
import com.zoga.yun.kotlin.project.NetUtils
import java.io.Serializable

abstract class KotlinActivity : AppCompatActivity(),BaseInterface, View.OnLayoutChangeListener
    {

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
     * 将对象集合的某一字符串字段进行拼接
     * @param list ArrayList<T> 对象集合
     * @param regex String 分隔符
     * @param func (Int)->String 根据集合索引来获取字符串
     * @return String
     */
    fun <T> appendStr(list:ArrayList<T>,regex:String, func:(Int)->String): String {
        var build = StringBuilder()
        for (i in list.indices){
            if(i!=list.size-1){
                build.append(func.invoke(i)).append(regex)
            }else{
                build.append(func.invoke(i))
            }
        }
        return build.toString()
    }

    fun <T> appendStr(list:List<T>,regex:String, func:(Int)->String): String {
        var build = StringBuilder()
        for (i in list.indices){
            if(i!=list.size-1){
                build.append(func.invoke(i)).append(regex)
            }else{
                build.append(func.invoke(i))
            }
        }
        return build.toString()
    }

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
        return px2dip(this@KotlinActivity,this.toFloat())
    }

    fun Number.dp2px():Int{
        return dip2px(this@KotlinActivity,this.toFloat())
    }

    fun Number.sp():Int{
        return px2sp(this@KotlinActivity,this.toFloat())
    }

    fun Number.px():Int{
        return sp2px(this@KotlinActivity,this.toFloat())
    }

    /**
     * 获取屏幕宽高
     */
    fun Context.srnWidth():Int{
        return this.resources.displayMetrics.widthPixels
    }

    fun Context.srnHeight():Int{
        return this.resources.displayMetrics.heightPixels
    }


    /**
     * Activity类扩展
     */
    fun <T:Activity> Activity.go(cls:Class<T>, vararg pairs:Pair<String,Any>){
        val intent = Intent(this,cls)
        pairs.forEach {
            when(it.second::class){
                String::class->intent.putExtra(it.first,it.second.toString())
                Int::class->intent.putExtra(it.first,it.second as Int)
                Boolean::class->intent.putExtra(it.first,it.second as Boolean)
                Serializable::class->intent.putExtra(it.first,it.second as Serializable)
            }
        }
        startActivity(intent)
    }

    override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
        val keyHeight = this.windowManager.defaultDisplay.height / 10
        if (oldBottom != 0 && bottom != 0 && oldBottom - bottom > keyHeight) {
            onKeyboardShow()
        } else if (oldBottom != 0 && bottom != 0 && bottom - oldBottom > keyHeight) {
            onKeyboardHide()
        }
    }
    open fun onKeyboardHide() {}
    open fun onKeyboardShow() {}

    fun extraStr(name:String): String? {
        return intent.getStringExtra(name)
    }

    fun extraInt(pair:Pair<String,Int>): Int {
        return intent.getIntExtra(pair.first,pair.second)
    }

    fun extraBool(pair:Pair<String,Boolean>):Boolean{
        return intent.getBooleanExtra(pair.first,pair.second)
    }

    fun extraSerial(name:String): Serializable? {
        return intent.getSerializableExtra(name)
    }

    class Group(vararg anys: Any){
        var anys: Array<out Any> = anys

        fun text(vararg strs:String): Group {
            for ((i,it) in anys.withIndex()){
                (it as TextView).text = strs[i]
            }
            return this
        }

        fun textColor(vararg colors:Int): Group {
            for ((i,it) in anys.withIndex()){
                (it as TextView).setTextColor(colors[i])
            }
            return this
        }
    }

    inner class JsonList<T>{
        fun transList(jsonStr:String): List<T> {
            return gson.fromJson(jsonStr, object : TypeToken<List<T>>(){}.type) as List<T>
        }

        fun transArrayList(jsonStr:String): ArrayList<T> {
            return gson.fromJson(jsonStr, object : TypeToken<ArrayList<T>>(){}.type) as ArrayList<T>
        }
    }

    /**
     * 初始化侧滑菜单
     */
    fun initDrawer(drawerLayout:DrawerLayout,gravity:Int){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, gravity)
        drawerLayout.setDrawerListener(object : DrawerLayout.SimpleDrawerListener(){
            override fun onDrawerClosed(drawerView: View) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, gravity)
            }
        })
    }

    /**
     * 打开侧滑菜单
     */
    fun openDrawer(drawerLayout:DrawerLayout,gravity:Int){
        drawerLayout.openDrawer(gravity)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,gravity)
    }

    /**
     * 获取TextView的Drawable
     */
    fun tvDrawable(id:Int): Drawable {
        val drawable = resources.getDrawable(id)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        return drawable
    }

    fun TextView.setLeftTVDrawable(id:Int){
        this.setCompoundDrawables(tvDrawable(id),null,null,null)
    }

    fun TextView.setRightTVDrawable(id:Int){
        this.setCompoundDrawables(null,null,tvDrawable(id),null)
    }

    fun TextView.setTopTVDrawable(id:Int){
        this.setCompoundDrawables(null,tvDrawable(id),null,null)
    }

    fun TextView.setBtmTVDrawable(id:Int){
        this.setCompoundDrawables(null,null,null,tvDrawable(id))
    }

    fun TextView.setNullTVDrawable(){
        this.setCompoundDrawables(null,null,null,null)
    }

}