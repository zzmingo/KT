package com.enginekt.input

import com.enginekt.Context
import com.enginekt.base.dispose.Disposable

/**
 * Created by mingo on 2017/8/8.
 */
interface InputProcessor<in T : InputEvent> : Disposable {

    fun processInputEvent(context: Context, event: T)

}