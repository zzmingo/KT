package com.enginekt.input.pointer

import com.enginekt.Context
import com.enginekt.Entity
import com.enginekt.SortingLayers

/**
 * Created by mingo on 2017/8/8.
 */
class DefaultPointerInputProcessor(val multiple: Boolean = false) : PointerInputProcessorBase() {

    private var _pointerDown = false
    private var _pointerDownIdentifier: Int = 1
    private val _pointerOutMap = mutableMapOf<Entity, Boolean>()
    private val _targetHandlerMap = mutableMapOf<Entity, PointerEventHandler>()

    override fun processInputEvent(context: Context, event: PointerInputEvent) {
        when(event.type) {
            PointerInput.POINTER_DOWN -> _handlePointerDown(context, event)
            PointerInput.POINTER_MOVE -> _handlePointerMove(context, event)
            PointerInput.POINTER_UP -> _handlePointerUp(context, event)
            PointerInput.POINTER_CANCEL -> _handlePointerCancel(context, event)
        }
    }

    override fun dispose() {

    }

    private fun _handlePointerDown(context: Context, event: PointerInputEvent) {
        if (!multiple && _pointerDown) {
            return
        }

        _pointerDown = true
        _pointerDownIdentifier = event.identifier
        _targetHandlerMap.clear()
        _pointerOutMap.clear()

        val hits = findHits(event, context.stage.root)
        if (hits.isEmpty()) {
            return
        }

        val sortingLayers = context.renderingContext.sortingLayers
        hits.sortWith(Comparator { a, b ->
            val layerOrderA = sortingLayers.getOrder(a.renderer?.sortingLayer ?: SortingLayers.DEFAULT)
            val layerOrderB = sortingLayers.getOrder(b.renderer?.sortingLayer ?: SortingLayers.DEFAULT)
            val layerRet = layerOrderA - layerOrderB
            if (layerRet != 0) {
                layerRet
            } else {
                val orderRet = (a.renderer?.sortingOrder ?: 0) - (b.renderer?.sortingOrder ?: 0)
                if (orderRet != 0) {
                    orderRet
                } else {
                    hits.indexOf(a) - hits.indexOf(b)
                }
            }
        })

        val pointerEvent = PointerEvent(event.x, event.y, PointerEvent.POINTER_DOWN, event.identifier)
        for (hit in hits) {
            val handler = hit.get(PointerEventHandler::class)
            if (handler != null) {
                _targetHandlerMap[hit] = handler
                handler.onPointerEvent(pointerEvent)
                if (pointerEvent.consumed) {
                    return
                }
            }
        }
    }

    private fun _handlePointerMove(context: Context, event: PointerInputEvent) {
        if (!multiple && event.identifier != _pointerDownIdentifier) {
            return
        }
        if (_targetHandlerMap.isEmpty()) {
            return
        }

        for ((entity, handler) in _targetHandlerMap) {
            val outside = _pointerOutMap.contains(entity)
            val hit = hitTest(event, entity)
            if (hit) {
                handler.onPointerEvent(PointerEvent(event.x, event.y, PointerEvent.POINTER_MOVE, event.identifier))
            }
            if (hit && outside) {
                _pointerOutMap.remove(entity)
                handler.onPointerEvent(PointerEvent(event.x, event.y, PointerEvent.POINTER_ENTER, event.identifier))
            } else if (!hit && !outside) {
                _pointerOutMap.put(entity, true)
                handler.onPointerEvent(PointerEvent(event.x, event.y, PointerEvent.POINTER_OUT, event.identifier))
            }
        }
    }

    private fun _handlePointerUp(context: Context, event: PointerInputEvent) {
        if (!multiple && event.identifier != _pointerDownIdentifier) {
            return
        }
        if (_targetHandlerMap.isEmpty()) {
            return
        }

        for ((entity, handler) in _targetHandlerMap) {
            val hit = hitTest(event, entity)
            if (hit) {
                handler.onPointerEvent(PointerEvent(event.x, event.y, PointerEvent.POINTER_UP, event.identifier))
            } else {
                handler.onPointerEvent(PointerEvent(event.x, event.y, PointerEvent.POINTER_UP_OUTSIDE, event.identifier))
            }
        }
    }

    private fun _handlePointerCancel(context: Context, event: PointerInputEvent) {
        if (!multiple && event.identifier != _pointerDownIdentifier) {
            return
        }
        if (_targetHandlerMap.isEmpty()) {
            return
        }

        for ((_, handler) in _targetHandlerMap) {
            handler.onPointerEvent(PointerEvent(event.x, event.y, PointerEvent.POINTER_CANCEL, event.identifier))
        }
    }

}
