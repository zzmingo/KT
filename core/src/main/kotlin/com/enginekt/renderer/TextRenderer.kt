package com.enginekt.renderer

import com.enginekt.base.observable.ObservableVector2
import com.enginekt.base.observable.observable
import com.enginekt.core.RendererBase

/**
 * Created by mingo on 2017/8/14.
 */
abstract class TextRenderer : RendererBase() {

    companion object {
        val STYLE = "style"
        val ANCHOR = "anchor"
        val TEXT = "text"
    }

    val style: TextStyle = TextStyle()
    val anchor: ObservableVector2 = ObservableVector2(0.5, 0.5)
    var text: String by observable("")

    init {
        addDisposable(anchor.OnChange.add { ->
            onPropertyChange(ANCHOR, anchor, null)
        })
        addDisposable(style.OnChange.add { ->
            onPropertyChange(STYLE, anchor, null)
        })
    }

}