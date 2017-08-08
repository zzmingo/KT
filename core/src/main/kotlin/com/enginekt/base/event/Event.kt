package com.enginekt.base.event

import com.enginekt.base.dispose.Disposable
import com.enginekt.base.utils.SafeList

typealias VoidListener = () -> Unit
typealias Listener<T> = (T) -> Unit


class Event<T> : Disposable {

    private val listeners = SafeList<Listener<T>>()
    private val voidListeners = SafeList<VoidListener>()

    fun invoke(data: T) {
        if (listeners.size > 0) {
            listeners.forEach { it(data) }
        }
        if (voidListeners.size > 0) {
            voidListeners.forEach { it() }
        }
    }

    fun add(listener: VoidListener): Disposable {
        voidListeners.add(listener)
        return object: Disposable {
            override fun dispose() {
                remove(listener)
            }
        }
    }

    fun add(listener: Listener<T>): Disposable {
        listeners.add(listener)
        return object: Disposable {
            override fun dispose() {
                remove(listener)
            }
        }
    }

    fun remove(listener: VoidListener) {
        voidListeners.remove(listener)
    }

    fun remove(listener: Listener<T>) {
        listeners.remove(listener)
    }

    fun clear() {
        listeners.clear()
    }

    override fun dispose() {
        clear()
    }
}