package com.test.mercadolibre.repository

import com.test.mercadolibre.model.ResponseProduct
import io.reactivex.Single

interface MercadoLibreRepository {
    fun getProducts(searchProduct: String): Single<ResponseProduct>
}