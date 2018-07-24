package com.kotlin_base.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zoga.yun.kotlin.BaseInterface
import com.zoga.yun.kotlin.LayoutId

abstract class KotlinFragment:Fragment(),BaseInterface {


    /** Fragment当前状态是否可见  */
    var isVis: Boolean = false
    var mHasLoadedOnce = false
    var isPrepared = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewInject = this::class.annotations[0] as LayoutId
        val view = inflater.inflate(viewInject.id,container,false)
        init(view)
        isPrepared = true
        return view
    }

    abstract fun init(view: View?)

    /**
     * 土司提示
     * @param isLong 是否显示更长时间
     */
    fun Any.toast(isLong: Boolean=false){
        if(isLong)
            Toast.makeText(activity,this.toString(),
                    Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(activity,this.toString(),
                    Toast.LENGTH_LONG).show()
    }

}