package com.enginekt.base

interface XY {
    val x: Number
    val y: Number
}

typealias UnitFun = () -> Unit

typealias Task = () -> Unit

typealias CallbackFun<T> = (T) -> Unit