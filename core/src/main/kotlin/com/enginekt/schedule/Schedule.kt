package com.enginekt.schedule

import com.enginekt.base.dispose.Disposable

interface Schedule : Disposable {

    val id: String
    val paused: Boolean
    val completed: Boolean
    val canceled: Boolean

    fun pause()
    fun resume()
    fun cancel()

}