package com.enginekt

interface Renderer {

    var sortingLayer: String
    var sortingOrder: Int
    var alpha: Double
    var color: Int

    fun render(context: RenderingContext)
    fun updateTransform()

}