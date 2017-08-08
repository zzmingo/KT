package com.enginekt.platform.dom

import com.enginekt.Component
import com.enginekt.CoreFactory
import com.enginekt.Entity
import com.enginekt.KT
import com.enginekt.platform.dom.renderer.DomSpriteRenderer
import com.enginekt.renderer.SpriteRenderer
import kotlin.reflect.KClass

class DomCoreFactory : CoreFactory {

    override fun create(): Entity {
        val entity = DomEntity(KT.app)
        KT.app.stage.addEntity(entity)
        entity.initDOMEntity()
        return entity
    }

    @Suppress("UNUSED_VARIABLE")
    override fun <T : Component> create(clazz: KClass<T>): T {
        if (clazz === SpriteRenderer::class) {
            return DomSpriteRenderer().unsafeCast<T>()
        } else {
            val JSClass = clazz.js.asDynamic()
            return js("new JSClass()").unsafeCast<T>()
        }
    }
}