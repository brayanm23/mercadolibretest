package com.test.mercadolibre.repository.api

import com.test.mercadolibre.model.ResponseProduct
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MercadoLibreApi {

    @GET("search")
    fun getProducts(@Query("q") product: String): Single<ResponseProduct>

}