package com.enginekt.rendering

import com.enginekt.Renderer
import com.enginekt.RenderingContext

class RenderCommand (
        override val sortingLayer: String,
        override val sortingOrder: Int,
        override var index: Int,
        val renderer: Renderer
) : Command {

    override fun execute(context: RenderingContext) {
        renderer.render(context)
    }

}