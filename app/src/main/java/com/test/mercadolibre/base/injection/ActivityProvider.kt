package com.test.mercadolibre.base.injection

import com.test.mercadolibre.base.injection.scopes.PerActivity
import com.test.mercadolibre.injection.FragmentProvider
import com.test.mercadolibre.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityProvider {

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity


}