package com.enginekt.platform.dom.renderer

import com.enginekt.RenderingContext
import com.enginekt.platform.dom.DomRenderingContext
import com.enginekt.platform.dom.anchor
import com.enginekt.platform.dom.image
import com.enginekt.renderer.SpriteRenderer
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class DomSpriteRenderer : SpriteRenderer(), DomInstanceDelegate {

    private val _domInstance = DomInstance(this, this)

    init {
        addDisposable(OnPropertyChange.add({ event ->
            when (event.name) {
                TEXTURE -> _updateTexture()
                ANCHOR -> _updateAnchor()
            }
        }))
    }

    override fun initDomElement(): HTMLElement {
        val element = document.createElement("div") as HTMLDivElement
        _updateTexture()
        _updateAnchor()
        return element
    }

    override fun updateTransform() {
        _domInstance.updateTransform()
    }

    override fun render(context: RenderingContext) {
        _domInstance.render(context as DomRenderingContext)
    }

    fun _updateTexture() {
        _domInstance.edit { it.image(texture) }
    }

    fun _updateAnchor() {
        _domInstance.edit({ it.anchor(anchor) })
    }

}