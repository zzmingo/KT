package com.enginekt

import com.enginekt.base.utils.Callback
import com.enginekt.base.dispose.Disposable
import kotlin.reflect.KClass

interface FileSystem : Disposable {

    fun <T : Asset> retain(path: String, clazz: KClass<T>, callback: Callback<T>)
    fun release(asset: Asset)

    fun texture(path: String, callback: Callback<Texture>) {
        retain(path, Texture::class, callback)
    }

}