package  com.test.mercadolibre.base.injection.network

import android.content.Context
import com.test.mercadolibre.base.injection.scopes.PerApplication
import com.test.mercadolibre.base.injection.qualifiers.AppContext
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named


@Module
class NetworkModule {

    @Named("Access")
    @Provides
    @PerApplication
    fun provideInterceptor(@AppContext context: Context): InterceptorRequest = InterceptorRequest()

    @Named("SlowWithPublicKey")
    @Provides
    @PerApplication
    fun provideSlowWithAccessTokenRetrofitInstance(@Named("Slow") okHttpClientBuilder: OkHttpClient.Builder,
                                                   @Named("Access") interceptorRequest: InterceptorRequest,
                                                   @Named("BaseUrl") baseUrl: String): Retrofit {
        return getFeatureRetrofitBuilder( okHttpClientBuilder, interceptorRequest, baseUrl).build()
    }

    @Named("Slow")
    @Provides
    @PerApplication
    fun provideSlowOkHttpClientBuilder(): OkHttpClient.Builder {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
        return okHttpClientBuilder
    }

    @Named("BaseUrl")
    @Provides
    @PerApplication
    fun providerBaseUrl(): String = "https://api.mercadolibre.com/sites/MLA/"

    private fun getFeatureRetrofitBuilder(okHttpClientBuilder: OkHttpClient.Builder,
                                          interceptorRequest: InterceptorRequest, baseUrl: String): Retrofit.Builder {
        okHttpClientBuilder.interceptors().apply {
            clear()
            add(interceptorRequest)
        }
        val client = okHttpClientBuilder.build()
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)

    }

}