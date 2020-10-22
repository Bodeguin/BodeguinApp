package pe.edu.upc.bodeguin.data.network.service

import pe.edu.upc.bodeguin.data.network.model.request.AuthRequest
import pe.edu.upc.bodeguin.data.network.model.request.SignUpRequest
import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthService {
    @POST("api/usuarios/log")
    suspend fun authenticate(@Body authRequest: AuthRequest): Response<AuthResponse>
    @POST("api/usuarios")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<AuthResponse>
    @PUT("api/usuarios/{id}")
    suspend fun updateUserApi(@Path("id") id: Int, @Body signUpRequest: SignUpRequest): Response<AuthResponse>
    @GET("api/usuarios/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<AuthResponse>
}