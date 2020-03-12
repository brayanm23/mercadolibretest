package com.test.mercadolibre.base.model

import android.util.Log
import com.google.gson.Gson
import retrofit2.HttpException

data class FailedResponse constructor(val status: String?,
                                      val error: String?,
                                      val cause: Any?,
                                      val message: String?) {

    companion object {
        fun build(exception: HttpException): FailedResponse {
            val errorJsonString = exception.response()
                    .errorBody()?.string()
            Log.d("Failed Response:", "$errorJsonString")
            return Gson().fromJson(errorJsonString, FailedResponse::class.java)
        }
    }

}