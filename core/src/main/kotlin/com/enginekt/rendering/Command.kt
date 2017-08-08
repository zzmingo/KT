package com.enginekt.rendering

import com.enginekt.RenderingContext

interface Command {

    val sortingLayer: String
    val sortingOrder: Int
    var index: Int

    fun execute(context: RenderingContext)

}