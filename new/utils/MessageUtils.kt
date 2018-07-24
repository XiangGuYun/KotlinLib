package com.zoga.yun.kotlin.utils

import android.os.Message

interface MessageUtils {
    fun Message.setWhat(what:Int): Message {
        this.what=what
        return this
    }
}