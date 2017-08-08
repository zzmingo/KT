package com.enginekt.core

import com.enginekt.*

/**
 * Created by mingo on 2017/8/8.
 */
class StageImpl(app: Application) : Entity(app), Stage {

    override val viewport = Viewport()
    override val root: Entity = this

    val rootTransform = Transform()

    init {
        addDisposable(viewport.OnChange.add(this::onViewportChange))
    }

    override fun initialize() {
        init()
        onViewportChange()
    }

    fun updateStage() {
        val rootDirty = rootTransform.dirty
        if (rootDirty) {
            rootTransform.updateWorldMatrix(null, false)
        }
        updateTransform(rootTransform, rootDirty)
        update()
    }

    fun renderStage(context: RenderingContext) {
        render(context)
    }

    fun onViewportChange() {
        val scaleX = view.width / viewport.size.x
        val scaleY = view.height / viewport.size.y
        rootTransform.scale.set(scaleX, scaleY)
        rootTransform.position.set(viewport.center.x * scaleX, viewport.center.y * scaleY)
    }

    override fun addEntity(entity: Entity) {
        add(entity)
    }

    override fun removeEntity(entity: Entity) {
        remove(entity)
    }
}