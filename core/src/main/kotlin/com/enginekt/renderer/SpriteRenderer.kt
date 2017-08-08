package com.enginekt.renderer

import com.enginekt.Texture
import com.enginekt.base.math.Vector2
import com.enginekt.base.observable.ObservableVector2
import com.enginekt.base.observable.observable
import com.enginekt.core.RendererBase

abstract class SpriteRenderer : RendererBase() {

    companion object {
        val TEXTURE = "texture"
        val ANCHOR = "anchor"
    }

    var texture: Texture? by observable(null)
    val anchor: ObservableVector2 = ObservableVector2(0.5, 0.5)

    init {
        addDisposable(anchor.OnChange.add { ->
            onPropertyChange(ANCHOR, anchor, null)
        })
    }

}