package com.enginekt.input

import com.enginekt.base.dispose.Disposable
import com.enginekt.base.event.Event

interface Input<T : InputEvent> : Disposable {

    val OnInput: Event<T>

    fun fireEvent(event: T) {
        OnInput.invoke(event)
    }

}