package com.enginekt.platform.web

import com.enginekt.MathLibrary
import kotlin.js.Math

class WebMathLibrary : MathLibrary {

    override val E: Double = 2.7182818284590452354
    override val PI: Double = Math.PI

    override fun abs(value: Number): Number {
        return Math.abs(value.toDouble())
    }

    override fun sin(value: Double): Double {
        return Math.sin(value)
    }

    override fun cos(value: Double): Double {
        return Math.cos(value)
    }
}