package com.enginekt.base.utils

/**
 * Created by mingo on 17/8/9.
 */
interface Callback<in T> {
    fun success(result: T)
    fun error(error: Throwable) {}
}