package com.test.mercadolibre.base.injection

import com.test.mercadolibre.base.injection.network.NetworkModule
import com.test.mercadolibre.base.injection.scopes.PerApplication
import com.test.mercadolibre.base.injection.tools.ToolsModule
import com.test.mercadolibre.injection.ApiModule
import com.test.mercadolibre.injection.FragmentProvider
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityProvider::class,
    ToolsModule::class,
    ApiModule::class,
    NetworkModule::class,
    //ViewModelModule::class,
    FragmentProvider::class
])

interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
