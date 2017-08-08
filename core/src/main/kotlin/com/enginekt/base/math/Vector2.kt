package com.enginekt.base.math

import com.enginekt.base.XY

open class Vector2(x: Number, y: Number) : XY {

    override var x: Double = 0.0

    override var y: Double = 0.0

    init {
        this.x = x.toDouble()
        this.y = y.toDouble()
    }

    constructor() : this(0.0, 0.0)

    fun clone(): Vector2 {
        return Vector2(x, y)
    }

    fun set(x: Number, y: Number) {
        this.x = x.toDouble()
        this.y = y.toDouble()
    }

    fun set(xy: XY) {
        set(xy.x, xy.y)
    }

}