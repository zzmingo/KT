package com.enginekt.platform.dom.renderer

import com.enginekt.Component
import com.enginekt.Context
import com.enginekt.RenderingContext
import com.enginekt.core.RendererBase
import com.enginekt.platform.dom.DomRenderingContext
import com.enginekt.platform.dom.css
import com.enginekt.platform.dom.transform
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document

interface DomInstanceDelegate {
    fun initDomElement(): HTMLElement
}

/**
 * Created by mingo on 2017/8/7.
 */
class DomInstance(val renderer: RendererBase, delegate: DomInstanceDelegate) : DomInstanceDelegate by delegate, Context by renderer  {

    val wrap: HTMLDivElement by lazy { document.createElement("div") as HTMLDivElement }

    val element: HTMLElement by lazy { delegate.initDomElement() }

    private var _sortingLayerDirty = true
    private var _sortingOrderDirty = true

    init {
        renderer.OnInit.add(this::onInit)
        renderer.OnInit.add(this::onDestroy)
        renderer.OnPropertyChange.add(this::onPropertyChange)
    }

    fun onInit() {
        wrap.css(mapOf(
                "position" to "absolute",
                "top" to "0",
                "left" to "0",
                "background-color" to "transparent",
                "transform-origin" to "0 0"
        ))
        wrap.appendChild(element)
    }

    fun onDestroy() {
        wrap.parentNode?.removeChild(wrap)
    }

    fun onPropertyChange(change: Component.PropertyChange?) {
        when (change!!.name) {
            RendererBase.ALPHA -> wrap.style.opacity = change.value.toString()
            RendererBase.SORTING_LAYER -> _sortingLayerDirty = true
            RendererBase.SORTING_ORDER -> _sortingOrderDirty = true
        }
    }

    fun updateTransform() {
        logger.debug(DomInstance::class.simpleName!!, "updateTransform")
        wrap.transform(renderer.entity.transform.worldMatrix)
    }

    fun render(context: DomRenderingContext) {
        if (_sortingOrderDirty) {
            _sortingOrderDirty = false
            wrap.style.zIndex = renderer.sortingOrder.toString()
        }
        if (_sortingLayerDirty) {
            _sortingLayerDirty = false
            context.renderToLayer(wrap, renderer.sortingLayer)
        }
    }

}