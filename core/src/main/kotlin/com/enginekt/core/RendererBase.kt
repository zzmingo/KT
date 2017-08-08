package com.enginekt.core

import com.enginekt.Component
import com.enginekt.Renderer
import com.enginekt.SortingLayers
import com.enginekt.base.observable.observable

abstract class RendererBase : Component(), Renderer {

    companion object {
        val SORTING_LAYER = "sortingLayer"
        val SORTING_ORDER = "sortingOrder"
        val ALPHA = "alpha"
        val COLOR = "color"
    }

    override var sortingLayer: String by observable(SortingLayers.DEFAULT)
    override var sortingOrder: Int by observable(0)

    override var alpha: Double by observable(1.0)
    override var color: Int by observable(0xFFFFFF)

}