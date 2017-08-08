package com.enginekt

import com.enginekt.base.dispose.CompositeDisposableBase
import com.enginekt.base.utils.SafeList
import com.enginekt.schedule.Scheduler
import kotlin.reflect.KClass

abstract class Entity constructor(val context: Context) : CompositeDisposableBase(), Context by context {

    var parent: Entity? = null
        get() = field
        private set(value) { field = value }

    var name: String? = null

    var active: Boolean = true
    var visible: Boolean = true
    var interactive: Boolean = false
    var interactiveChildren: Boolean = true

    var inited: Boolean = false
        get() = field
        private set(value) { field = value }

    var destroyed: Boolean = false
        get() = field
        private set(value) { field = value }

    val transform = Transform()
    var renderer: Renderer? = null
        get() = field
        private set(value) { field = value }

    override val scheduler = Scheduler()

    private val children = SafeList<Entity>()
    private val components = SafeList<Component>()
    private val behaviours = SafeList<Behaviour>()

    fun add(child: Entity) {
        child.parent?.remove(child)
        child.parent = this
        children.add(child)
    }

    fun remove(child: Entity) {
        children.remove(child)
    }

    fun <T : Component> add(clazz: KClass<T>) {
        val component = this.context.coreFactory.create(clazz)
        add(component)
        if (inited) {
            component.init()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(clazz: KClass<T>): T? {
        if (components.size == 0) {
            return null
        }
        return components.find { clazz.isInstance(it) } as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getAll(clazz: KClass<T>): List<T> {
        return components.filter { clazz.isInstance(it) } as List<T>
    }

    fun forEach(action: (Entity) -> Unit) {
        return children.forEach(action)
    }

    protected fun add(component: Component) {
        components.add(component)
        if (component is Renderer) {
            renderer = component
        }
        if (component is Behaviour) {
            behaviours.add(component)
        }
        component.setEntity(this)
    }

    protected fun remove(component: Component) {
        components.remove(component)
        if (component is Renderer) {
            renderer = null
        }
        if (component is Behaviour) {
            behaviours.remove(component)
        }
    }

    protected fun init() {
        children.forEach { it.init() }
        components.forEach { it.init() }
        inited = true
    }

    protected fun destroy() {
        dispose()
        children.forEach { it.destroy() }
        components.forEach { it.destroy() }
        destroyed = true
    }

    protected fun updateTransform(parent: Transform?, parentDirty: Boolean) {
        val dirty = parentDirty || transform.dirty
        if (!inited || !active) {
            transform.dirty = dirty
            return
        }
        transform.updateWorldMatrix(parent, dirty)
        if (dirty) {
            renderer?.updateTransform()
        }
        children.forEach { it.updateTransform(transform, dirty) }
    }

    protected fun update() {
        if (!inited || !active) {
            return
        }
        behaviours.forEach { it.update() }
        scheduler.update(KT.app.time.delta)
        children.forEach { it.update() }
    }

    protected fun render(renderingContext: RenderingContext) {
        if (!inited || !active || !visible) {
            return
        }
        if (renderer != null) {
            val renderer = this.renderer!!
            renderingContext.addRenderCommand(renderer.sortingLayer, renderer.sortingOrder, renderer)
        }
        children.forEach { it.render(renderingContext) }
    }

}