package com.test.mercadolibre.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.test.mercadolibre.RxImmediateSchedulerRule
import com.test.mercadolibre.TestConstants.ERROR_MESSAGE
import com.test.mercadolibre.TestConstants.PRODUCT_SEARCH
import com.test.mercadolibre.TestConstants.RESPONSE_PRODUCT
import com.test.mercadolibre.TestConstants.RESPONSE_PRODUCT_RESULT_EMPTY
import com.test.mercadolibre.repository.MercadoLibreRepository
import io.reactivex.Single
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchProductUseCaseImplTest {
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val instantTaskRule = InstantTaskExecutorRule()


    @Mock
    private lateinit var mercadoLibreRepository: MercadoLibreRepository

    @Mock
    private lateinit var observer: Observer<SearchProductsUseCase.Result>

    private lateinit var subject: SearchProductsUseCaseImpl

    private val useCaseLiveData: MediatorLiveData<SearchProductsUseCase.Result> = MediatorLiveData()

    private fun givenASetUpSubject() {
        useCaseLiveData.observeForever { observer }
        subject = SearchProductsUseCaseImpl(mercadoLibreRepository)
    }

    @Test
    fun `GIVEN the services search products are retrieved succesfully WHEN the products are fetched THEN the products sees them in screen`() {
        givenASetUpSubject()
        BDDMockito.given(mercadoLibreRepository.getProducts(PRODUCT_SEARCH)).willReturn(Single.just(RESPONSE_PRODUCT))

        subject.search(PRODUCT_SEARCH)

        Truth.assertThat(subject.getLiveData().value).isEqualTo(SearchProductsUseCase.Result.Success(RESPONSE_PRODUCT))
    }

    @Test
    fun `GIVEN the services search products but result is empty succesfully WHEN the navigate fragment correct THEN display screen`() {
        givenASetUpSubject()
        BDDMockito.given(mercadoLibreRepository.getProducts(PRODUCT_SEARCH)).willReturn(Single.just(RESPONSE_PRODUCT_RESULT_EMPTY))

        subject.search(PRODUCT_SEARCH)

        Truth.assertThat(subject.getLiveData().value).isEqualTo(SearchProductsUseCase.Result.ListIsEmpty)
    }


    @Test
    fun `GIVEN a network or server error WHEN redirect to error screen THEN an error is shown`() {
        givenASetUpSubject()
        BDDMockito.given(mercadoLibreRepository.getProducts(PRODUCT_SEARCH)).willReturn(Single.error(Exception(ERROR_MESSAGE)))

        subject.search(PRODUCT_SEARCH)

        Truth.assertThat(subject.getLiveData().value).isEqualTo(SearchProductsUseCase.Result.Error(ERROR_MESSAGE))
    }


}