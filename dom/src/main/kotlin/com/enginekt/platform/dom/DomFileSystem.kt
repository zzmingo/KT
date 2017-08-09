package com.enginekt.platform.dom

import com.enginekt.Asset
import com.enginekt.Texture
import com.enginekt.base.utils.Callback
import com.enginekt.core.FileSystemBase
import org.w3c.dom.HTMLImageElement
import kotlin.reflect.KClass

class DomFileSystem : FileSystemBase(false) {

    override fun <T : Asset> load(path: String, clazz: KClass<T>, callback: Callback<T>) {
        when (clazz) {
            Texture::class -> loadTexture(path, object: Callback<Texture> {
                override fun success(asset: Texture) {
                    callback.success(asset.unsafeCast<T>())
                }

                override fun error(error: Throwable) {
                    callback.error(error)
                }
            })
            else -> {
                throw IllegalArgumentException("Unsupported asset class: ${clazz.simpleName}")
            }
        }
    }

    fun loadTexture(path: String, callback: Callback<Texture>) {
        val image = js("new Image()")
        image.src = path
        image.onload = {
            callback.success(DomTexture(path, image as HTMLImageElement))
        }
        image.onerror = {
             callback.error(Error("Fail to load texture: $path"))
        }
    }

}