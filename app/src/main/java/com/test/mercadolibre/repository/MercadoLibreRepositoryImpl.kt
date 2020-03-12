package com.test.mercadolibre.repository

import com.test.mercadolibre.model.ResponseProduct
import com.test.mercadolibre.repository.api.MercadoLibreApi
import io.reactivex.Single
import javax.inject.Inject

class MercadoLibreRepositoryImpl @Inject constructor(val api: MercadoLibreApi): MercadoLibreRepository{

    override fun getProducts(searchProduct: String): Single<ResponseProduct> = api.getProducts(searchProduct)

}