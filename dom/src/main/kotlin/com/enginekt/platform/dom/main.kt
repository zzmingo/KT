package com.enginekt.platform.dom

import com.enginekt.KT
import com.enginekt.Texture
import com.enginekt.base.utils.Callback
import com.enginekt.renderer.SpriteRenderer
import org.w3c.dom.HTMLDivElement
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    val root = document.getElementById("root") as HTMLDivElement
    val app = DomApplication(root, 0x000000)
    app.useDefaultPointerInput(false)
    app.start()
    app.post {
        app.stage.viewport.center.set(window.innerWidth/2, window.innerHeight/2)
        app.stage.viewport.size.set(window.innerWidth, window.innerHeight)
        val entity = KT.entity()

        val sprite = entity.add(SpriteRenderer::class)
        app.fs.texture("kotlin_250x250.png", object: Callback<Texture> {
            override fun success(result: Texture) {
                sprite.texture = result
            }
        })
    }
}