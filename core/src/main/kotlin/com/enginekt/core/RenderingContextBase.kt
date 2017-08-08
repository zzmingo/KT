package com.enginekt.core

import com.enginekt.Renderer
import com.enginekt.RenderingContext
import com.enginekt.SortingLayers
import com.enginekt.rendering.Command
import com.enginekt.rendering.RenderCommand

abstract class RenderingContextBase : RenderingContext {

    override val sortingLayers = SortingLayers()
    private val _commands = mutableListOf<Command>()

    override fun addCommand(command: Command) {
        command.index = _commands.size
        _commands.add(command)
    }

    override fun addRenderCommand(sortingLayer: String, sortingOrder: Int, renderer: Renderer) {
        addCommand(RenderCommand(sortingLayer, sortingOrder, _commands.size, renderer))
    }

    override fun flush() {
        _commands.sortWith(Comparator { a, b ->
            val layerRet = sortingLayers.getOrder(a.sortingLayer) - sortingLayers.getOrder(b.sortingLayer)
            if (layerRet != 0) {
                layerRet
            } else {
                val orderRet = a.sortingOrder - b.sortingOrder
                if (orderRet != 0) {
                    orderRet
                } else {
                    a.index - b.index
                }
            }
        })
        _commands.forEach { it.execute(this) }
    }


}