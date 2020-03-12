package com.test.mercadolibre.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T>(private val viewLiveData: MediatorLiveData<T> = MediatorLiveData()) : ViewModel() {

    fun getState(): LiveData<T> = viewLiveData


    fun setState(state: T) {
        viewLiveData.value = state
    }

    fun <S> listenSource(source: LiveData<S>, onChanged: (param: S) -> Unit) {
        viewLiveData.addSource(source, onChanged)
    }

}