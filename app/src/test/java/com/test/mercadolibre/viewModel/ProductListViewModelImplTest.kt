package com.test.mercadolibre.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.test.mercadolibre.TestConstants
import com.test.mercadolibre.TestConstants.ERROR_MESSAGE
import com.test.mercadolibre.TestConstants.PRODUCT_SEARCH
import com.test.mercadolibre.TestConstants.RESPONSE_PRODUCT
import com.test.mercadolibre.domain.SearchProductsUseCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductListViewModelImplTest {

    @Rule
    @JvmField
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var subject: ProductListViewModelImpl

    private val searchProductsUseCaseLiveData: MutableLiveData<SearchProductsUseCase.Result> = MutableLiveData()

    @Mock
    private lateinit var observer: Observer<ProductListViewModel.State>

    @Mock
    private lateinit var searchProductsUseCase: SearchProductsUseCase

    private fun givenASetUpSubject() {
        BDDMockito.given(searchProductsUseCase.getLiveData()).willReturn(searchProductsUseCaseLiveData)

        subject = ProductListViewModelImpl(searchProductsUseCase)
        subject.getState().observeForever{ observer }
    }

    @Test
    fun `GIVEN a succesfull response WHEN the products are fetched THEN the products are listed in screen`() {
        givenASetUpSubject()

        BDDMockito.given(searchProductsUseCase.search(PRODUCT_SEARCH)).will { setUseCaseLiveDataResult(SearchProductsUseCase.Result.Success(RESPONSE_PRODUCT)) }

        subject.searchProduct(PRODUCT_SEARCH)

        Truth.assertThat(subject.getState().value).isEqualTo(ProductListViewModel.State.Success(RESPONSE_PRODUCT))
    }

    @Test
    fun `GIVEN a succesfull response but result is empty WHEN the navigate fragment correct THEN display screen`() {
        givenASetUpSubject()

        BDDMockito.given(searchProductsUseCase.search(PRODUCT_SEARCH)).will { setUseCaseLiveDataResult(SearchProductsUseCase.Result.ListIsEmpty) }

        subject.searchProduct(PRODUCT_SEARCH)

        Truth.assertThat(subject.getState().value).isEqualTo(ProductListViewModel.State.ListIsEmpty)
    }


    @Test
    fun `GIVEN a server error WHEN redirect to error screen THEN an error is shown`() {
        givenASetUpSubject()

        BDDMockito.given(searchProductsUseCase.search(PRODUCT_SEARCH)).will { setUseCaseLiveDataResult(SearchProductsUseCase.Result.Error(ERROR_MESSAGE)) }

        subject.searchProduct(PRODUCT_SEARCH)

        Truth.assertThat(subject.getState().value).isEqualTo(ProductListViewModel.State.Error(ERROR_MESSAGE))
    }

    @Test
    fun `GIVEN a network error WHEN redirect to error screen THEN an error is shown`() {
        givenASetUpSubject()

        BDDMockito.given(searchProductsUseCase.search(PRODUCT_SEARCH)).will { setUseCaseLiveDataResult(SearchProductsUseCase.Result.Failure(ERROR_MESSAGE)) }

        subject.searchProduct(PRODUCT_SEARCH)

        Truth.assertThat(subject.getState().value).isEqualTo(ProductListViewModel.State.Error(ERROR_MESSAGE))
    }

    private fun setUseCaseLiveDataResult(result: SearchProductsUseCase.Result) {
        searchProductsUseCaseLiveData.value = result
    }

}