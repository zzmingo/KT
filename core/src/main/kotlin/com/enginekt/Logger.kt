package com.enginekt;

import com.enginekt.base.dispose.Disposable

interface Logger : Disposable {

    fun print(vararg o: Any?)

    fun debug(tag: String, vararg o: Any?)

    fun info(tag: String, vararg o: Any?)

    fun warn(tag: String, vararg o: Any?)

    fun error(tag: String, vararg o: Any?)

}
