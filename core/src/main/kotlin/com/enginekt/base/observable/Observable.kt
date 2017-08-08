package com.enginekt.base.observable

import kotlin.reflect.KProperty

interface Observable {
    fun onPropertyChange(name: String, value: Any?, old: Any?)
}

class ObservableProperty<in T : Observable, P>(initialValue: P) {

    private var value = initialValue

    operator fun getValue(thisRef: T?, property: KProperty<*>): P {
        return value
    }

    operator fun setValue(thisRef: T?, property: KProperty<*>, value: P) {
        val oldValue = this.value
        if (value == oldValue) {
            return
        }
        this.value = value
        thisRef!!.onPropertyChange(property.name, value, oldValue)
    }
}

fun <T : Observable, P> observable(initialValue: P): ObservableProperty<T, P> {
    return ObservableProperty(initialValue)
}