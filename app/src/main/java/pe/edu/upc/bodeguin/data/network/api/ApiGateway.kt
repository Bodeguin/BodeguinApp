package pe.edu.upc.bodeguin.data.network.api

import pe.edu.upc.bodeguin.data.network.service.AuthService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiGateway {
    private const val baseUrl = "http://192.168.1.9:8080/"
    fun instance(): AuthService {
        val api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return api.create(AuthService::class.java)
    }

}