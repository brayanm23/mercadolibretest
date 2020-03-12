package com.test.mercadolibre.viewModel

import com.test.mercadolibre.domain.SearchProductsUseCase
import com.test.mercadolibre.domain.SearchProductsUseCase.Result
import javax.inject.Inject

class ProductListViewModelImpl  @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
): ProductListViewModel() {

    private var positionProductList = 0

    init {
        setState(State.Loading)
        listenSource(searchProductsUseCase.getLiveData(), ::onProductsResult)
    }

    override fun searchProduct(searchProduct: String) {
        searchProductsUseCase.search(searchProduct)
    }

    override fun savePositionProductList(position: Int) {
        positionProductList = position
    }

    override fun getPositionProductList() = positionProductList

    private fun onProductsResult(result: Result) {
        when(result) {
            is Result.Success -> setState(State.Success(result.listProduct))
            is Result.ListIsEmpty -> setState(State.ListIsEmpty)
            is Result.Error -> setState(State.Error(result.errorMessage))
            is Result.Failure -> setState(State.Error(result.failedResponseCode))
        }
    }

}