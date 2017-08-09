package com.enginekt.core

import kotlin.reflect.KClass
import com.enginekt.Asset
import com.enginekt.FileSystem
import com.enginekt.base.dispose.Disposable
import com.enginekt.base.event.Event
import com.enginekt.base.utils.Callback

abstract class FileSystemBase(var autoRelease: Boolean = false) : FileSystem {

    private val OnLoad = Event<Asset>()

    private val _cache = mutableMapOf<String, Asset>()
    private val _referenceMap = mutableMapOf<String, Int>()
    private val _loadingMap = mutableMapOf<String, Int>()

    private var disposed: Boolean = false

    @Suppress("UNCHECKED_CAST")
    override fun <T : Asset> retain(path: String, clazz: KClass<T>, callback: Callback<T>) {
        val asset = _cache[path]
        if (asset != null) {
            return callback.success(asset as T)
        }
        if (_loadingMap.contains(path)) {
            _increase(path, _loadingMap)
            var listener: Disposable? = null
            listener = OnLoad.add { asset: Asset ->
                listener?.dispose()
                _decrease(path, _loadingMap)
                callback.success(asset as T)
            }
            return
        }
        _increase(path, _loadingMap)
        load(path, clazz, object: Callback<T> {
            override fun success(asset: T) {
                if (!disposed) {
                    _cache[asset.path] = asset
                    _increase(path, _referenceMap)
                    _decrease(path, _loadingMap)
                    callback.success(asset)
                    OnLoad.invoke(asset)
                }
            }
            override fun error(error: Throwable) {
                callback.error(error)
            }
        })
    }

    override fun release(asset: Asset) {
        _decrease(asset.path, _referenceMap)

        if (!autoRelease) {
            return
        }
        if (_loadingMap.contains(asset.path)) {
            return
        }
        if (_referenceMap.contains(asset.path)) {
            return
        }
        val asset = _cache[asset.path]!!
        asset.dispose()
        _cache.remove(asset.path)
        _referenceMap.remove(asset.path)
    }

    override fun dispose() {
        disposed = true
        OnLoad.clear()
        _referenceMap.clear()
        _loadingMap.clear()
        _cache.forEach { it.value.dispose() }
    }

    protected abstract fun <T : Asset> load(path: String, clazz: KClass<T>, callback: Callback<T>)

    private fun _increase(key: String, map: MutableMap<String, Int>) {
        if (!map.containsKey(key)) {
            map[key] = 1
        } else {
            map[key] = map[key]!! + 1
        }
    }

    private fun _decrease(key: String, map: MutableMap<String, Int>) {
        val count = map[key]!! - 1
        map[key] = count
        if (count == 0) {
            map.remove(key)
        }
    }
}