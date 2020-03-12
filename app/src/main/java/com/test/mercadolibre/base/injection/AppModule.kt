package com.test.mercadolibre.base.injection

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.test.mercadolibre.base.injection.qualifiers.AppContext
import com.test.mercadolibre.base.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    @AppContext
    abstract fun provideContext(app: App): Context

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}
