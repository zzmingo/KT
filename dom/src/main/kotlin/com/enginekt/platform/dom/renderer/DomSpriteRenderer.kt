package com.enginekt.platform.dom.renderer

import com.enginekt.RenderingContext
import com.enginekt.platform.dom.DomRenderingContext
import com.enginekt.renderer.SpriteRenderer
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class DomSpriteRenderer : SpriteRenderer(), DomInstanceDelegate {

    private val _domInstance = DomInstance(this, this)

    init {
        addDisposable(OnPropertyChange.add({ event ->
            if (event.name == SpriteRenderer.TEXTURE) {
                _updateTexture()
            }
        }))
    }

    override fun initDomElement(): HTMLElement {
        return document.createElement("div") as HTMLDivElement
    }

    override fun updateTransform() {
        _domInstance.updateTransform()
    }

    override fun render(context: RenderingContext) {
        _domInstance.render(context as DomRenderingContext)
    }

    fun _updateTexture() {
        val element = _domInstance.element
        
    }

}