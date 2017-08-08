package com.enginekt

import com.enginekt.base.dispose.Disposable
import kotlin.reflect.KClass

typealias FSCallback<T> = (T) -> Unit

interface FileSystem : Disposable {

    fun <T : Asset> retain(path: String, clazz: KClass<T>, onComplete: FSCallback<T>)
    fun release(asset: Asset)

    fun texture(path: String, onComplete: FSCallback<Texture>) {
        retain(path, Texture::class, onComplete)
    }

}