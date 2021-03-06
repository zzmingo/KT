package com.enginekt.platform.web

import com.enginekt.core.ApplicationBase
import com.enginekt.input.pointer.DefaultPointerInputProcessor
import org.w3c.dom.HTMLDivElement
import kotlin.browser.window
import kotlin.js.Date

abstract class WebApplication(
        val root: HTMLDivElement,
        resolution: Double
) : ApplicationBase(resolution) {

    final override val logger: WebLogger = WebLogger()
    final override val math: WebMathLibrary = WebMathLibrary()

    init {
        addDisposable(logger)
    }

    private var _animationFrameId: Int = -1

    override fun currentMillis(): Long {
        return Date().getTime().toLong()
    }

    override fun start() {
        super.start()
        _doStep()
    }

    override fun stop() {
        if (started) {
            window.cancelAnimationFrame(_animationFrameId)
        }
        super.stop()
    }

    override fun useDefaultPointerInput(multiple: Boolean) {
        useInputSuite(WebPointerInput(this, root), DefaultPointerInputProcessor(multiple))
    }

    private fun _doStep() {
        _animationFrameId = window.requestAnimationFrame {
            if (started) {
                step()
                _doStep()
            }
        }
    }

}