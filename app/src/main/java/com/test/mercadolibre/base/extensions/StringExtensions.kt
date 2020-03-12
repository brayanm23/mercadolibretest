package com.test.mercadolibre.base.extensions

fun String.replaceHttpToHttps(): String {
    return this.replace("http","https")
}