package com.enginekt.input.pointer

import com.enginekt.Entity
import com.enginekt.input.InputProcessor

/**
 * Created by mingo on 2017/8/8.
 */
abstract class PointerInputProcessorBase : InputProcessor<PointerInputEvent> {

    fun findHits(event: PointerInputEvent, entity: Entity, result: MutableList<Entity> = mutableListOf<Entity>()): MutableList<Entity> {
        if (!entity.active || !entity.visible) {
            return result
        }

        if (entity.interactive && hitTest(event, entity)) {
            result.add(entity)
        }

        if (entity.interactiveChildren) {
            entity.forEach { findHits(event, entity, result) }
        }
        return result
    }

    fun hitTest(event: PointerInputEvent, entity: Entity): Boolean {
        val hitArea = entity.get(HitArea::class)
        return (hitArea?.interactive ?: false) && (hitArea?.contains(event.x, event.y) ?: false)
    }

}