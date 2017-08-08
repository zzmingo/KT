package com.enginekt

import com.enginekt.base.dispose.Disposable
import com.enginekt.rendering.Command

interface RenderingContext : Disposable {

    val sortingLayers: SortingLayers

    fun initialize()

    fun clear()

    fun addCommand(command: Command)

    fun addRenderCommand(sortingLayer: String, sortingOrder: Int, renderer: Renderer)

    fun flush()

}