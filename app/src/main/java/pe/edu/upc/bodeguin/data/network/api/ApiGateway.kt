package pe.edu.upc.bodeguin.data.network.api

import okhttp3.OkHttpClient
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.network.service.AppService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiGateway {
    private const val baseUrl = "http://192.168.1.9:80/"
    fun instance(
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): AppService {

        val timeout: Long = 7000

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor)
            .connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .build()

        val api = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return api.create(AppService::class.java)
    }
}