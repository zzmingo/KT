package com.enginekt

object KT {

    val app: Application
        get() = (_app ?: _singleApp)!!

    private var _app: Application? = null
    private var _singleApp: Application? = null
    private var _appList = mutableListOf<Application>()

    fun entity(): Entity {
        if (_app == null) {
            throw IllegalStateException("在多个Application实例下禁止在主循环外创建Entity")
        }
        return app.coreFactory.create()
    }

    internal fun enterLoop(app: Application) {
        _app = app
    }

    internal fun exitLoop() {
        _app = null
    }

    internal fun onAppCreate(app: Application) {
        _appList.add(app)
        if (_appList.size == 1) {
            _singleApp = app
        } else {
            _singleApp = null
        }
    }

    internal fun onAppDispose(app: Application) {
        _appList.remove(app)
        if (_appList.size == 1) {
            _singleApp = app
        } else {
            _singleApp = null
        }
    }

}