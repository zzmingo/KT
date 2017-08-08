package com.enginekt.platform.dom

import com.enginekt.base.math.Matrix3
import com.enginekt.base.math.Vector2
import org.w3c.dom.HTMLElement

@Suppress("UNUSED_PARAMETER")
fun hex2string(hex: Int): String {
    var hexStr = js("hex.toString(16)").unsafeCast<String>()
    hexStr = "000000".substring(0, 6 - hexStr.length) + hexStr
    return "#$hexStr"
}

fun toCSSMatrix(matrix: Matrix3): String {
    val m = matrix
    return "matrix(${m.a}, ${m.b}, ${m.c}, ${m.d}, ${m.tx}, ${m.ty})"
}

private val prefixKeys = listOf("transform", "transform-origin", "text-stroke-width", "text-stroke-color")

fun setStyles(element: HTMLElement, styles: Map<String, Any>) {
    styles.forEach {
        element.style.setProperty(it.key, it.value.toString())
    }
    prefixKeys.forEach {
        if (styles.containsKey(it)) {
            element.style.setProperty("-webkit-$it", styles[it].toString())
        }
    }
}

fun HTMLElement.transform(matrix: Matrix3) {
    val cssMatrix = toCSSMatrix(matrix)
    this.style.setProperty("transform", cssMatrix)
    this.style.setProperty("-webkit-transform", cssMatrix)
}

fun HTMLElement.css(styles: Map<String, Any>) {
    setStyles(this, styles)
}