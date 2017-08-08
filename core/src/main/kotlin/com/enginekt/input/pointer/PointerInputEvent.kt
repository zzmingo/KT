package com.enginekt.input.pointer

import com.enginekt.input.InputEvent

/**
 * Created by mingo on 2017/8/8.
 */
class PointerInputEvent(
        override val type: String,
        val x: Double,
        val y: Double,
        val identifier: Int
) : InputEvent