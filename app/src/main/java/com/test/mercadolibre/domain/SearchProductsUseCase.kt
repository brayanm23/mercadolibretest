package com.test.mercadolibre.domain

import com.test.mercadolibre.base.domain.UseCase
import com.test.mercadolibre.model.ResponseProduct

interface SearchProductsUseCase: UseCase<SearchProductsUseCase.Result> {
    sealed class Result {
        data class Success(val listProduct: ResponseProduct): Result()
        object ListIsEmpty: Result()
        data class Error(val errorMessage: String): Result()
        data class Failure(val failedResponseCode: String): Result()
    }

    fun search(searchProduct: String)
}