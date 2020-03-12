package com.test.mercadolibre.base.extensions


fun Int.convertToCents(): Int = this * 100

infix fun Int.combinedWith(b: Int): Int = this or b

infix fun Int.removing(b: Int): Int = this and b.inv()

infix fun Int.includesFlag(b: Int): Boolean = this and b == b