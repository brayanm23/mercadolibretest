package com.test.mercadolibre.base.injection.tools

import com.test.mercadolibre.base.injection.scopes.PerApplication
import com.test.mercadolibre.base.routing.FeatureRouter
import com.test.mercadolibre.base.routing.FeatureRouterImpl
import dagger.Module
import dagger.Provides
import timber.log.Timber
import java.util.*
import javax.inject.Named

@Module
class ToolsModule {

    @Provides
    @PerApplication
    fun provideFeatureRouter(): FeatureRouter = FeatureRouterImpl()

    @Provides
    @PerApplication
    fun provideLogger(): Timber.Tree = Timber.DebugTree()

    @Named("Locale")
    @Provides
    @PerApplication
    fun provideLocale(): Locale = Locale("es", "AR")

}