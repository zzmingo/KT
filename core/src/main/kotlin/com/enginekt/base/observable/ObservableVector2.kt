package com.enginekt.base.observable

import com.enginekt.base.event.Event
import com.enginekt.base.math.Vector2


class ObservableVector2(x: Number, y: Number) : Vector2() {

    val OnChange = Event<Any>()

    override var x: Double
        get() = super.x
        set(value) {
            if (super.x == value) {
                return
            }
            super.x = value
            OnChange?.invoke(this)
        }

    override var y: Double
        get() = super.y
        set(value) {
            if (super.y == value) {
                return
            }
            super.y = value
            OnChange?.invoke(this)
        }

    init {
        this.x = x.toDouble()
        this.y = y.toDouble()
    }

    constructor() : this(0, 0)
}