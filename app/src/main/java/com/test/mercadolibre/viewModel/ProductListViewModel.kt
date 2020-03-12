package com.test.mercadolibre.viewModel

import com.test.mercadolibre.base.viewmodel.BaseViewModel
import com.test.mercadolibre.model.ResponseProduct

abstract class ProductListViewModel: BaseViewModel<ProductListViewModel.State>() {
    sealed class State {
        object Loading: State()
        data class Success(val response: ResponseProduct): State()
        object ListIsEmpty: State()
        data class Error(val error: String): State()
    }

    abstract fun searchProduct(searchProduct: String)
    abstract fun savePositionProductList(position: Int)
    abstract fun getPositionProductList(): Int
}