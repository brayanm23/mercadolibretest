package com.test.mercadolibre.base.injection

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

open class App : DaggerApplication() {

    @Inject
    lateinit var loggingTree: Timber.Tree

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(loggingTree)
    }

}