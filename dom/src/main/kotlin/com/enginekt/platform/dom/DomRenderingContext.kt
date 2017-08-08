package com.enginekt.platform.dom

import com.enginekt.core.RenderingContextBase
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class DomRenderingContext(
        val view: DomView,
        val background: Int
) : RenderingContextBase() {

    private val _sortingLayerMap = mutableMapOf<String, HTMLDivElement>()

    override fun initialize() {
        view.element.style.background = hex2string(background)
        _initSortingLayers()
    }

    override fun clear() {}

    override fun dispose() {
        _sortingLayerMap.forEach { it.value.parentNode!!.removeChild(it.value) }
        _sortingLayerMap.clear()
    }

    fun renderToLayer(element: HTMLElement, sortingLayer: String) {
        element.parentNode?.removeChild(element)
        _sortingLayerMap[sortingLayer]!!.appendChild(element)
    }

    fun _initSortingLayers() {
        sortingLayers.forEach {
            val div = document.createElement("div") as HTMLDivElement
            div.setAttribute("KT-layer-name", it.key)
            div.setAttribute("KT-layer-order", it.value.toString())
            div.css(mapOf(
                    "position" to "absolute",
                    "top" to "0",
                    "left" to "0",
                    "width" to "100%",
                    "height" to "100%",
                    "z-index" to it.value.toString(),
                    "background-color" to "transparent"
            ))
            view.element.appendChild(div)
            _sortingLayerMap[it.key] = div
        }
    }
}