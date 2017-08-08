package com.enginekt.platform.dom

import com.enginekt.Texture
import org.w3c.dom.HTMLImageElement

class DomTexture(override val path: String, val image: HTMLImageElement) : Texture {

    override val id: String = image.src
    override val width: Int = image.width
    override val height: Int = image.height

    val url: String = image.src

    override fun dispose() {
    }
}