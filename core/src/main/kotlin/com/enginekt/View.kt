package com.enginekt

import com.enginekt.base.dispose.Disposable
import com.enginekt.base.event.Event

/**
 * Created by mingo on 2017/8/8.
 */
interface View : Disposable {

    val width: Int
    val height: Int

    val OnResize: Event<Any>

}