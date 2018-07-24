package com.zoga.yun.kotlin.project

import android.content.Context
import android.net.ConnectivityManager
import com.zoga.yun.net.OK


interface NetUtils {

    fun <T> OK<T>.getReq(cls: Class<T>, url: String, fun1: (data: T, msg: String) -> Unit,
                         fun2: (eCode: Int, msg: String) -> Unit, vararg args: String) {
        get(cls, url, fun1, fun2, args)
    }

    fun <T> OK<T>.postReq(cls: Class<T>, url: String, fun1: (data: T, msg: String) -> Unit,
                          fun2: (eCode: Int, msg: String) -> Unit, vararg args: String) {
        post(cls, url, fun1, fun2, args)
    }

    /**
     * @方法说明:判断是否有网络连接
     * @方法名称:isNetworkConnected
     * @return
     * @返回值:boolean
     */
    fun Context.netOK(): Boolean {
        val mConnectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager
                .activeNetworkInfo
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable
        }
        return false
    }

    enum class NetType {
        WIFI, CMNET, CMWAP, NONE
    }


    /**
     * @方法说明:获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap 网络3：net网络
     * @return
     * @返回值:NetType
     */
    fun Context.netType(): NetType {
        val connMgr =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo ?: return NetType.NONE
        val nType = networkInfo.type

        if (nType == ConnectivityManager.TYPE_MOBILE) {
            return if (networkInfo.extraInfo.toLowerCase() == "cmnet") {
                NetType.CMNET
            } else {
                NetType.CMWAP
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI
        }
        return NetType.NONE

    }
}