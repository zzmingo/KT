package com.enginekt.renderer

import com.enginekt.Texture
import com.enginekt.base.observable.observable
import com.enginekt.core.RendererBase

abstract class SpriteRenderer : RendererBase() {

    companion object {
        val TEXTURE = "texture"
    }

    var texture: Texture? by observable(null)

}