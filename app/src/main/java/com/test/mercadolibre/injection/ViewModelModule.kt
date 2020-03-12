package com.test.mercadolibre.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.test.mercadolibre.base.injection.qualifiers.ViewModelKey
import com.test.mercadolibre.base.viewmodel.ViewModelFactory
import com.test.mercadolibre.domain.SearchProductsUseCase
import com.test.mercadolibre.domain.SearchProductsUseCaseImpl
import com.test.mercadolibre.view.ProductListFragment
import com.test.mercadolibre.view.SearchFragment
import com.test.mercadolibre.viewModel.ProductListViewModel
import com.test.mercadolibre.viewModel.ProductListViewModelImpl
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    fun provideProductListViewModelIntoMap(paymentMethodsViewModel: ProductListViewModelImpl): ViewModel = paymentMethodsViewModel

    @Provides
    fun provideProductListFragmentViewModel(fragment: ProductListFragment, viewModelFactory: ViewModelFactory): ProductListViewModel =
        ViewModelProviders.of(fragment, viewModelFactory)[ ProductListViewModel::class.java]

    @Provides
    fun providesSearchProductsUseCaseViewModel(searchProductsUseCaseImpl: SearchProductsUseCaseImpl):
            SearchProductsUseCase = searchProductsUseCaseImpl

}
