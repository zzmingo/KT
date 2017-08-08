package com.enginekt.base.math

class Matrix3 (
        var a: Double = 1.0,
        var b: Double = 0.0,
        var c: Double = 0.0,
        var d: Double = 1.0,
        var tx: Double = 0.0,
        var ty: Double = 0.0
) {

    fun identity() {
        this.a = 1.0
        this.b = 0.0
        this.c = 0.0
        this.d = 1.0
        this.tx = 0.0
        this.ty = 0.0
    }

    fun invert() {
        val a1 = this.a
        val b1 = this.b
        val c1 = this.c
        val d1 = this.d
        val tx1 = this.tx
        val ty1 = this.ty
        val n = a1 * d1 - b1 * c1
        this.a = d1 / n
        this.b = -b1 / n
        this.c = -c1 / n
        this.d = a1 / n
        this.tx = (c1 * ty1 - d1 * tx1) / n
        this.ty = -(a1 * ty1 - b1 * tx1) / n
    }

    fun set(a: Double, b: Double, c: Double, d: Double, tx: Double, ty: Double) {
        this.a = a
        this.b = b
        this.c = c
        this.d = d
        this.tx = tx
        this.ty = ty
    }

    fun set(mat: Matrix3) {
        set(mat.a, mat.b, mat.c, mat.d, mat.tx, mat.ty)
    }
    
    fun append(a: Double, b: Double, c: Double, d: Double, tx: Double, ty: Double) {
        val a1 = this.a
        val b1 = this.b
        val c1 = this.c
        val d1 = this.d
        val tx1 = this.tx
        val ty1 = this.ty
        this.a = a * a1 + b * c1
        this.b = a * b1 + b * d1
        this.c = c * a1 + d * c1
        this.d = c * b1 + d * d1
        this.tx = tx * a1 + ty * c1 + tx1
        this.ty = tx * b1 + ty * d1 + ty1
    }

}