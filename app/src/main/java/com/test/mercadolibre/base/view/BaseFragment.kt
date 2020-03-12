package com.test.mercadolibre.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.mercadolibre.base.routing.FeatureRouter
import com.test.mercadolibre.base.viewmodel.BaseViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<V, S> : BaseViewInterface<S, V>, DaggerFragment() where V : BaseViewModel<S> {

    @Inject
    lateinit var featureRouter: FeatureRouter

    @Inject
    lateinit var viewModel: V

    override fun getDomainViewModel(): V {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStateObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        startInitialDomainAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cleanUp()
    }


}
