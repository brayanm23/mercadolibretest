package com.test.mercadolibre.domain

import com.test.mercadolibre.R
import com.test.mercadolibre.base.domain.BaseUseCase
import com.test.mercadolibre.base.extensions.errorMessage
import com.test.mercadolibre.model.ResponseProduct
import com.test.mercadolibre.repository.MercadoLibreRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchProductsUseCaseImpl @Inject constructor(val mercadoLibreRepository: MercadoLibreRepository) : SearchProductsUseCase, BaseUseCase<SearchProductsUseCase.Result>(){

    override fun search(searchProduct: String) {
        mercadoLibreRepository.getProducts(searchProduct)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleResponse, ::onError)
            .track()
    }

    private fun handleResponse(response: ResponseProduct) {
        liveData.value = if(response.results.isEmpty()) SearchProductsUseCase.Result.ListIsEmpty else SearchProductsUseCase.Result.Success(response)
    }

    override fun getErrorResult(throwable: Throwable): SearchProductsUseCase.Result? {
        return SearchProductsUseCase.Result.Error(throwable.errorMessage)
    }

    override fun getFailureResult(failedResponseCode: String): SearchProductsUseCase.Result? {
        return SearchProductsUseCase.Result.Failure(failedResponseCode)
    }
}