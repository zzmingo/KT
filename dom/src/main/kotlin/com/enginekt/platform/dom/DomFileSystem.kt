package com.enginekt.platform.dom

import com.enginekt.Asset
import com.enginekt.FSCallback
import com.enginekt.Texture
import com.enginekt.core.FileSystemBase
import org.w3c.dom.HTMLImageElement
import kotlin.reflect.KClass

class DomFileSystem : FileSystemBase(false) {

    override fun <T : Asset> load(path: String, clazz: KClass<T>, onComplete: FSCallback<T>) {
        when (clazz) {
            Texture::class -> loadTexture(path, { texture ->
                onComplete(texture.unsafeCast<T>())
            })
            else -> {
                throw IllegalArgumentException("Unsupported asset class: ${clazz.simpleName}")
            }
        }
    }

    fun loadTexture(path: String, onComplete: FSCallback<Texture>) {
        val image = js("new Image()")
        image.src = path
        image.onload = {
            onComplete(DomTexture(path, image as HTMLImageElement))
        }
        image.onerror = {
            throw RuntimeException("Fail to load texture: $path")
        }
    }

}