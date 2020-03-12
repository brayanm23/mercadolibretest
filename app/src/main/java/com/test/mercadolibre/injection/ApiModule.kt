package com.test.mercadolibre.injection

import com.test.mercadolibre.repository.MercadoLibreRepository
import com.test.mercadolibre.repository.MercadoLibreRepositoryImpl
import com.test.mercadolibre.repository.api.MercadoLibreApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named


@Module
class ApiModule {

    @Provides
    fun providesMercadoPagoApi(@Named("SlowWithPublicKey") retrofit: Retrofit): MercadoLibreApi {
        return retrofit.create(MercadoLibreApi::class.java)
    }

    @Provides
    fun provideMercadoPagoRepository(repository: MercadoLibreRepositoryImpl): MercadoLibreRepository = repository
}