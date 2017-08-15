package com.enginekt

import com.enginekt.base.DEG_TO_RAD
import com.enginekt.base.math.Matrix3
import com.enginekt.base.observable.Observable
import com.enginekt.base.observable.ObservableVector2
import com.enginekt.base.observable.observable

class Transform : Observable {

    val position = ObservableVector2()
    val scale = ObservableVector2(1.0, 1.0)
    val rotation: Double by observable(0.0)
    val pivot = ObservableVector2()
    val skew = ObservableVector2()

    val worldMatrix = Matrix3()

    var dirty: Boolean = true

    init {
        this.position.OnChange.add(this::onChange)
        this.scale.OnChange.add(this::onChange)
        this.pivot.OnChange.add(this::onChange)
        this.skew.OnChange.add(this::onChange)
    }

    fun updateWorldMatrix(parent: Transform? = null, force: Boolean = false) {
        if (!dirty && !force) {
            return
        }
        dirty = false

        val math = KT.app.math
        val cos: Double?
        val sin: Double?
        val r: Double?
        val worldMatrix = this.worldMatrix
        val x = this.position.x
        val y = this.position.y
        val rotation = this.rotation
        val scaleX = this.scale.x
        val scaleY = this.scale.y
        var skewX = this.skew.x
        var skewY = this.skew.y
        val pivot = this.pivot
        if (parent != null) {
            worldMatrix.set(parent.worldMatrix)
        } else {
            worldMatrix.identity()
        }
        if (rotation % 360 != 0.0) {
            r = rotation * DEG_TO_RAD
            cos = math.cos(r)
            sin = math.sin(r)
        } else {
            cos = 1.0
            sin = 0.0
        }
        if (skewX != 0.0 || skewY != 0.0) {
            skewX *= DEG_TO_RAD
            skewY *= DEG_TO_RAD
            worldMatrix.append(math.cos(skewY), math.sin(skewY), -math.sin(skewX), math.cos(skewX), x, y)
            worldMatrix.append(cos * scaleX, sin * scaleX, -sin * scaleY, cos * scaleY, 0.0, 0.0)
        } else {
            worldMatrix.append(cos * scaleX, sin * scaleX, -sin * scaleY, cos * scaleY, x, y)
        }
        if (pivot.x != 0.0 || pivot.y != 0.0) {
            worldMatrix.tx -= pivot.x * worldMatrix.a + pivot.y * worldMatrix.c
            worldMatrix.ty -= pivot.x * worldMatrix.b + pivot.y * worldMatrix.d
        }
    }

    private fun onChange() {
        dirty = true
    }

    override fun onPropertyChange(name: String, value: Any?, old: Any?) {
        dirty = true
    }
}