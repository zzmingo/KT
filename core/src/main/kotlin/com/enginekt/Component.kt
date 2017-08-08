package com.enginekt

import com.enginekt.base.dispose.CompositeDisposableBase
import com.enginekt.base.dispose.Disposable
import com.enginekt.base.event.Event
import com.enginekt.base.observable.Observable

abstract class Component private constructor(context: Context) : CompositeDisposableBase(), Observable, Context by context {

    val OnInit = Event<Any>()
    val OnDestroy = Event<Any>()
    val OnPropertyChange = Event<PropertyChange>()

    val entity: Entity
        get() = _entity!!

    private var _entity: Entity? = null

    constructor(): this(KT.app)

    fun init() {
        OnInit.invoke(this)
    }

    fun destroy() {
        dispose()
        OnDestroy.invoke(this)
    }

    override fun onPropertyChange(name: String, value: Any?, old: Any?) {
        this.OnPropertyChange.invoke(PropertyChange(name, value, old))
    }

    internal fun setEntity(entity: Entity) {
        _entity = entity
    }

    data class PropertyChange(val name: String, val value: Any?, val old: Any?)

}