package com.enginekt

import com.enginekt.base.dispose.Disposable
import com.enginekt.base.event.Event
import com.enginekt.base.observable.ObservableVector2

/**
 * Created by mingo on 2017/8/8.
 */
class Viewport : Disposable {

    val OnChange = Event<Viewport>()

    val center: ObservableVector2 = ObservableVector2()
    val size: ObservableVector2 = ObservableVector2(100, 100)

    init {
        center.OnChange.add({ -> OnChange.invoke(this) })
        size.OnChange.add({ -> OnChange.invoke(this) })
    }

    override fun dispose() {
        center.OnChange.clear()
        size.OnChange.clear()
        OnChange.clear()
    }

}