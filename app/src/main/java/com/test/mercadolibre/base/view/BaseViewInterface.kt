package com.test.mercadolibre.base.view

import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import com.test.mercadolibre.base.extensions.observe
import com.test.mercadolibre.base.viewmodel.BaseViewModel

interface BaseViewInterface<S, V> where V : BaseViewModel<S> {

    @LayoutRes
    fun getLayoutId(): Int

    fun getDomainViewModel(): V
    fun onStateChanged(state: S)

    fun initViews() {}
    fun startInitialDomainAction() {}
    fun cleanUp() {}

}


fun <T, S, V> T.initStateObservers() where V : BaseViewModel<S>, T : LifecycleOwner, T : BaseViewInterface<S, V> {
    observe(getDomainViewModel().getState()) { onStateChanged(it) }
}
