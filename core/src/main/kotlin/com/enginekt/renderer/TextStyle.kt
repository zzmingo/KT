package com.enginekt.renderer

import com.enginekt.base.event.Event
import com.enginekt.base.observable.Observable
import com.enginekt.base.observable.observable

/**
 * Created by mingo on 2017/8/14.
 */
class TextStyle : Observable {

    companion object {
        val FONT: String = "-apple-system, \".SFNSText-Regular\", \"SF UI Text\", \"Helvetica Neue\", Arial, " +
                "\"PingFang SC\", \"Hiragino Sans GB\", \"Microsoft YaHei\", \"WenQuanYi Zen Hei\", sans-serif"
    }

    val OnChange = Event<TextStyle>()

    var fill: Int by observable(0x000000)
    var fontSize: Int by observable(14)
    var fontWeight: String by observable("normal")
    var fontFamily: String by observable(FONT)
    var fontStyle: String by observable("normal")

    var stroke: Int by observable(0xFFFFFF)
    var strokeThickness: Int by observable(0)

    var wordWrap: Boolean by observable(false)
    var wordWrapWidth: Int by observable(100)
    var limitWidth: Int by observable(0)
    var ellipsed: Boolean by observable(false)

    override fun onPropertyChange(name: String, value: Any?, old: Any?) {
        OnChange.invoke(this)
    }
}