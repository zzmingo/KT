package com.enginekt.input.pointer

import com.enginekt.input.Input


/**
 * Created by mingo on 2017/8/8.
 */
interface PointerInput : Input<PointerInputEvent> {

    companion object {
        val POINTER_DOWN = "pointerdown"
        val POINTER_MOVE = "pointermove"
        val POINTER_UP = "pointerup"
        val POINTER_CANCEL = "pointercancel"
    }

}