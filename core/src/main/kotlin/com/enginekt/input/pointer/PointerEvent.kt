package com.enginekt.input.pointer

/**
 * Created by mingo on 2017/8/8.
 */
class PointerEvent(
    val x: Double,
    val y: Double,
    val type: String,
    val identifier: Int
) {

    companion object {
        val POINTER_DOWN = "pointerdown"
        val POINTER_MOVE = "pointermove"
        val POINTER_UP = "pointerup"
        val POINTER_UP_OUTSIDE = "pointerupoutside"
        val POINTER_CANCEL = "pointercancel"
        val POINTER_OUT = "pointerout"
        val POINTER_ENTER = "pointerenter"
    }

    var consumed: Boolean = false
        get() = field
        private set(value) { field = value }

    fun consume() {
        consumed = true
    }
}
