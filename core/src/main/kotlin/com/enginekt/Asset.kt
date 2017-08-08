package com.enginekt

import com.enginekt.base.dispose.Disposable

interface Asset : Disposable {
    val id: String
    val path: String
}