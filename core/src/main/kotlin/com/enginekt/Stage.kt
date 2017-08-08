package com.enginekt

/**
 * Created by mingo on 2017/8/8.
 */
interface Stage {

    val viewport: Viewport
    val root: Entity
    fun initialize()
    fun addEntity(entity: Entity)
    fun removeEntity(entity: Entity)

}