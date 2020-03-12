package com.test.mercadolibre.injection

import com.test.mercadolibre.view.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideSearchProductFragment(): SearchFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun provideProductListFragment(): ProductListFragment

    @ContributesAndroidInjector
    abstract fun provideDetailProductFragment(): DetailProductFragment

    @ContributesAndroidInjector
    abstract fun provideUnespectedErrorFragment(): UnespectedErrorFragment

    @ContributesAndroidInjector
    abstract fun provideResultNoFoundFragment(): ResultNoFoundFragment




}