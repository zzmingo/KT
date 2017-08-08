package com.enginekt.input.pointer

/**
 * Created by mingo on 2017/8/8.
 */
interface HitArea {

    val interactive: Boolean
        get() = true

    fun contains(x: Double, y: Double): Boolean
}