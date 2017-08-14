package com.enginekt.platform.dom.renderer

import com.enginekt.RenderingContext
import com.enginekt.platform.dom.*
import com.enginekt.renderer.TextRenderer
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document

/**
 * Created by mingo on 2017/8/14.
 */
class DomTextRenderer : TextRenderer(), DomInstanceDelegate {

    private val _domInstance = DomInstance(this, this)

    init {
        addDisposable(OnPropertyChange.add({ (name) ->
            when (name) {
                TEXT -> _updateText()
                ANCHOR -> _updateAnchor()
            }
        }))
    }

    override fun initDomElement(): HTMLElement {
        val element = document.createElement("div") as HTMLDivElement
        _updateText()
        _updateAnchor()
        _updateStyle()
        return element
    }

    override fun updateTransform() {
        _domInstance.updateTransform()
    }

    override fun render(context: RenderingContext) {
        _domInstance.render(context as DomRenderingContext)
    }

    fun _updateText() {
        _domInstance.edit { it.text(text) }
    }

    fun _updateAnchor() {
        _domInstance.edit({ it.anchor(anchor) })
    }

    fun _updateStyle() {
        _domInstance.edit {
            it.css(mapOf(
                    "color" to hex2string(colorMultiply(color, style.fill)),
                    "alpha" to alpha,
                    "fontSize" to "${style.fontSize}px",
                    "fontFamily" to style.fontFamily,
                    "fontStyle" to style.fontStyle,
                    "fontWeight" to style.fontWeight
            ))
            if (style.strokeThickness > 0) {
                it.css("textStrokeWidth", "${style.strokeThickness}px")
                it.css("textStrokeColor", hex2string(style.stroke))
            }
            if (style.limitWidth > 0) {
                it.css("overflow", if (style.ellipsed) "ellipsed" else "hidden")
                it.css("width", "${style.limitWidth}px")
                it.css("textAlign", "center")
            } else {
                if (style.wordWrap) {
                    it.css("whiteSpace", "wrap")
                    it.css("width", "${style.wordWrapWidth}px")
                } else {
                    it.css("whiteSpace", "nowrap")
                    it.css("width", "auto")
                }
            }
        }
    }

}