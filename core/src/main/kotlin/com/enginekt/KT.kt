package com.enginekt

object KT {

    val app: Application
        get() = _app!!

    private var _app: Application? = null

    fun entity(): Entity {
        if (_app == null) {
            throw IllegalStateException("禁止在主循环外创建Entity")
        }
        return app.coreFactory.create()
    }

    internal fun enterLoop(app: Application) {
        _app = app
    }

    internal fun exitLoop() {
        _app = null
    }

}