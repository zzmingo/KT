package com.enginekt.platform.dom

import com.enginekt.Application
import com.enginekt.View
import com.enginekt.base.event.Event
import org.w3c.dom.HTMLDivElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Math

/**
 * Created by mingo on 2017/8/8.
 */
class DomView(val app: Application, val root: HTMLDivElement) : View {

    val element = document.createElement("div") as HTMLDivElement

    override var width: Int = 0
        get() = field
        private set(value) { field = value }
    override var height: Int = 0
        get() = field
        private set(value) { field = value }

    override val OnResize = Event<Any>()

    init {
        element.id = "KT-dom-view"
        element.style.position = "relative"
        element.style.top = "0"
        element.style.left = "0"
        element.style.width = "100%"
        element.style.height = "100%"
        element.style.overflowX = "hidden"
        element.style.overflowY = "hidden"
        root.appendChild(element)
        updateSize()
        window.addEventListener("resize", { onWindowResize() })
    }

    override fun dispose() {
        element.parentNode!!.removeChild(element)
    }

    fun onWindowResize() {
        app.post {
            updateSize()
        }
    }

    fun updateSize() {
        val rect = root.getBoundingClientRect()
        width = Math.ceil(rect.width)
        height = Math.ceil(rect.height)
        OnResize.invoke(this)
    }
}