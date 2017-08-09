package com.enginekt.platform.dom

import com.enginekt.Application
import com.enginekt.platform.web.WebApplication
import org.w3c.dom.HTMLDivElement

class DomApplication(
        root: HTMLDivElement,
        background: Int = 0x000000,
        resolution: Double = 1.0
) : WebApplication(root, resolution) {

    override val platform = Application.Platform.DOM
    override val coreFactory = DomCoreFactory()
    override val fs = DomFileSystem()
    override val view = DomView(this, root)
    override val renderingContext = DomRenderingContext(view, background)

    init {
        addDisposable(fs)
        addDisposable(view)
        addDisposable(renderingContext)

        initialize()
    }

}