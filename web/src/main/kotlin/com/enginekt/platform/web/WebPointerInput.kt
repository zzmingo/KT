package com.enginekt.platform.web

import com.enginekt.Application
import com.enginekt.base.event.Event
import com.enginekt.base.math.Vector2
import com.enginekt.input.pointer.PointerInput
import com.enginekt.input.pointer.PointerInputEvent
import org.w3c.dom.DOMRect
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class WebPointerInput(val app: Application, root: HTMLDivElement) : PointerInput {

    override val OnInput = Event<PointerInputEvent>()

    val hook: HTMLDivElement = document.createElement("div") as HTMLDivElement

    private val _hookOffset: Vector2 = Vector2()
    private var _hookBoundingRect: DOMRect? = null


    private val _sharedVector = Vector2()
    private val _eventMap = mapOf(
            "touchstart" to PointerInput.POINTER_DOWN,
            "touchmove" to PointerInput.POINTER_MOVE,
            "touchend" to PointerInput.POINTER_UP,
            "touchcancel" to PointerInput.POINTER_CANCEL,
            "mousedown" to PointerInput.POINTER_DOWN,
            "mousemove" to PointerInput.POINTER_MOVE,
            "mouseup" to PointerInput.POINTER_UP,
            "mouseout" to PointerInput.POINTER_CANCEL
    )

    init {
        hook.style.position = "absolute"
        hook.style.top = "0"
        hook.style.left = "0"
        hook.style.width = "100%"
        hook.style.height = "100%"
        hook.id = "KT-WebInput-hook"
        root.appendChild(hook)

        _updateOffsetAndBounding()
        app.view.OnResize.add(this::_updateOffsetAndBounding)

        val isSupportTouch = js("!!(('ontouchstart' in window) || window.DocumentTouch && document instanceof window.DocumentTouch)") as Boolean
        if (isSupportTouch) {
            hook.addEventListener("touchstart", this::_handleDOMEvent, false)
            hook.addEventListener("touchmove", this::_handleDOMEvent, false)
            hook.addEventListener("touchend", this::_handleDOMEvent, false)
            hook.addEventListener("touchcancel", this::_handleDOMEvent, false)
        } else {
            var isMouseDown = false
            hook.addEventListener("mousedown", { event ->
                isMouseDown = true
                _handleDOMEvent(event)
            }, false)
            hook.addEventListener("mousemove", { event ->
                if (isMouseDown) {
                    _handleDOMEvent(event)
                }
            }, false)
            hook.addEventListener("mouseup", { event ->
                if (isMouseDown) {
                    _handleDOMEvent(event)
                }
            }, false)
            hook.addEventListener("mouseout", { event ->
                if (isMouseDown) {
                    _handleDOMEvent(event)
                }
            }, false)
        }
    }

    override fun dispose() {
        app.view.OnResize.remove(this::_updateOffsetAndBounding)
        hook.parentNode?.removeChild(hook)
    }

    private fun _handleDOMEvent(event: dynamic) {
        app.post {
            val nativeType = event.type.toString()
            val type = _eventMap[nativeType]!!
            if (nativeType.startsWith("mouse")) {
                val x = (event.pageX - this._hookOffset.x) as Double
                val y = (event.pageY - this._hookOffset.y) as Double
                _mapDOMPositionToPoint(x, y, _sharedVector)
                val inputEvent = PointerInputEvent(type, _sharedVector.x, _sharedVector.y, 1)
                fireEvent(inputEvent)
            } else {
                val touches = event.changedTouches
                val len = touches.length as Int
                for (i in 0..len-1) {
                    val touch = touches.item(i)
                    val identifer = touch.identifier as Int
                    val x = (touch.pageX - this._hookOffset.x) as Double
                    val y = (touch.pageY - this._hookOffset.y) as Double
                    _mapDOMPositionToPoint(x, y, _sharedVector)
                    val inputEvent = PointerInputEvent(type, _sharedVector.x, _sharedVector.y, identifer)
                    fireEvent(inputEvent)
                }
            }
        }

    }

    private fun _updateOffsetAndBounding() {
        var elt: HTMLElement = hook
        val offset = Vector2(elt.offsetLeft, elt.offsetTop)
        while (elt.offsetParent != null) {
            elt = elt.offsetParent as HTMLElement
            offset.x += elt.offsetLeft
            offset.y += elt.offsetTop
        }
        _hookOffset.set(offset)
        _hookBoundingRect = hook.getBoundingClientRect()
    }

    private fun _mapDOMPositionToPoint(x: Double, y: Double, point: Vector2 = Vector2()): Vector2 {
        val rect = _hookBoundingRect!!
        val resolution = app.resolution
        val resolutionMultiplier = 1 / resolution
        point.x = ((x - rect.x) * (app.view.width / rect.width)) * resolutionMultiplier
        point.y = ((y - rect.y) * (app.view.height / rect.height)) * resolutionMultiplier
        return point
    }
}