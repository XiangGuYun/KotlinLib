package com.kotlin_base.news.net

import com.google.gson.Gson
import com.kotlin_base.news.constant.API_KEY
import com.kotlin_base.news.constant.DN
import com.kotlin_base.news.constant.SITE
import com.kotlinlib.utils.StringUtils
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import java.lang.Exception


class NetUtils<T>: StringUtils {

    val gson = Gson()

    fun get(clazz: Class<T>,kw:String,pageToken:Int, onSuccess:(T)->Unit,
            onFailure:(String)->Unit){
        OkHttpUtils
                .get()
                .url(DN)
                .addParams("kw", kw)
                .addParams("pageToken", pageToken.toString())
                .addParams("site", SITE)
                .addParams("apikey", API_KEY)
                .build()
                .execute(object : StringCallback() {
                    override fun onError(call: Call?, e: Exception?, id: Int) {
                        onFailure.invoke(e?.message!!)
                        "网络请求错误：${e.message}".logE("NETUTILS")
                    }

                    override fun onResponse(response: String?, id: Int) {
                        "请求成功：$response".logD("NETUTILS")
                        val res = gson.fromJson<T>(response,clazz)
                        "解析结果:${gson.toJson(res)}".logD("NETUTILS")
                        onSuccess.invoke(res)
                    }
                })
    }

}