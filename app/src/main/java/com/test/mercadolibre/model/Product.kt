package com.test.mercadolibre.model

import java.io.Serializable

data class Product (
    val id: String,
    val title: String,
    val site_id: String,
    val currency_id: String,
    val condition: String,
    val thumbnail: String,
    val available_quantity: Int,
    val price: Float,
    val accepts_mercadopago: Boolean
): Serializable
