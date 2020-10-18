package pe.edu.upc.bodeguin.data.network.service

import pe.edu.upc.bodeguin.data.network.model.request.AuthRequest
import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/usuarios/log")
    fun authenticate(@Body authRequest: AuthRequest): Call<AuthResponse>
}