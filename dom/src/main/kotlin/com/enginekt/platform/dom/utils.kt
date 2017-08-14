package com.enginekt.platform.dom

import com.enginekt.Texture
import com.enginekt.base.math.Matrix3
import com.enginekt.base.math.Vector2
import org.w3c.dom.HTMLElement

@Suppress("UNUSED_PARAMETER")
fun hex2string(hex: Int): String {
    var hexStr = js("hex.toString(16)").unsafeCast<String>()
    hexStr = "000000".substring(0, 6 - hexStr.length) + hexStr
    return "#$hexStr"
}

fun colorMultiply(a: Int, b: Int): Int {
    val r = (((a shr 16) and 0xFF) / 255.0) * (((b shr 16) and 0xFF) / 255.0)
    val g = (((a shr 8) and 0xFF) / 255.0) * (((b shr 8) and 0xFF) / 255.0)
    val b = (((a and 0xFF) / 255.0) * ((b and 0xFF) / 255.0))
    return ((r * 255).toInt() shl 16) + ((g * 255).toInt() shl 8) + (b * 255).toInt()
}

fun toCSSMatrix(matrix: Matrix3): String {
    val m = matrix
    return "matrix(${m.a}, ${m.b}, ${m.c}, ${m.d}, ${m.tx}, ${m.ty})"
}

private val prefixMap = mapOf(
        "transform" to "webkit-transform",
        "transform-origin" to "webkit-transform-origin",
        "text-stroke-width" to "webkit-text-stroke-width",
        "text-stroke-color" to "webkit-text-stroke-color"
)

fun setStyle(element: HTMLElement, key: String, value: String) {
    element.style.setProperty(key, value)
    if (prefixMap.contains(key)) {
        element.style.setProperty(prefixMap[key]!!, value)
    }
}

fun setStyles(element: HTMLElement, styles: Map<String, Any>) {
    styles.forEach {
        setStyle(element, it.key, it.value.toString())
    }
}

fun HTMLElement.matrix(matrix: Matrix3) {
    val cssMatrix = toCSSMatrix(matrix)
    css("transform", cssMatrix)
}

fun HTMLElement.anchor(anchor: Vector2) {
    val translate = "translate(-${anchor.x*100}%, -${anchor.y*100}%)"
    css("transform", translate)
}

fun HTMLElement.image(texture: Texture?) {
    if (texture == null) {
        css("width", "0")
        css("height", "0")
        css("background-image", "none")
    } else {
        css("width", "${texture.width}px")
        css("height", "${texture.height}px")
        css("background-image", "url(${(texture as DomTexture).url})")
        css("background-size", "100% 100%")
    }
}

fun HTMLElement.text(text: String?) {
    this.innerText = text ?: ""
}

fun HTMLElement.css(styles: Map<String, Any>) {
    setStyles(this, styles)
}

fun HTMLElement.css(key: String, value: String) {
    setStyle(this, key, value)
}