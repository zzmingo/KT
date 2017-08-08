package com.enginekt

class SortingLayers {

    companion object {
        val DEFAULT = "Default"
    }

    private val _layerMap = mutableMapOf<String, Int>()

    init {
        add(DEFAULT, 0)
    }

    fun add(layer: String, order: Int) {
        _layerMap[layer] = order
    }

    fun getOrder(layer: String): Int {
        return _layerMap[layer]!!
    }

    fun forEach(action: (Map.Entry<String, Int>) -> Unit) {
        _layerMap.forEach(action)
    }

}