package com.test.mercadolibre.model

data class ResponseProduct (val query: String,
                            val results: List<Product>)