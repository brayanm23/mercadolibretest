package com.test.mercadolibre.base.extensions

inline var Throwable.errorMessage: String
    get() = localizedMessage?:message?:"Unknown error"
    private set(value) { }