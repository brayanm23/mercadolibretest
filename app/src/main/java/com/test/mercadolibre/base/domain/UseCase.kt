package com.test.mercadolibre.base.domain

import androidx.lifecycle.LiveData

interface UseCase<T> {

    fun getLiveData(): LiveData<T>

    fun cleanUp()

    fun onError(throwable: Throwable)

}