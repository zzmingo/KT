package com.enginekt.base.dispose

/**
 * Created by mingo on 2017/8/8.
 */
interface CompositeDisposable : Disposable {

    fun addDisposable(disposable: Disposable)

}

open class CompositeDisposableBase : CompositeDisposable {

    private val _disposableList = mutableListOf<Disposable>()

    final override fun addDisposable(disposable: Disposable) {
        _disposableList.add(disposable)
    }

    override fun dispose() {
        _disposableList.forEach { it.dispose() }
        _disposableList.clear()
    }
}