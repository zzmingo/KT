package com.enginekt.platform.web

import com.enginekt.Logger

class WebLogger : Logger {

    override fun print(vararg o: Any?) {
        console.log(*o)
    }

    override fun debug(tag: String, vararg o: Any?) {
        console.log(tag, *o)
    }

    override fun info(tag: String, vararg o: Any?) {
        console.info(tag, *o)
    }

    override fun warn(tag: String, vararg o: Any?) {
        console.warn(tag, *o)
    }

    override fun error(tag: String, vararg o: Any?) {
        console.error(tag, *o)
    }

    override fun dispose() {

    }
}