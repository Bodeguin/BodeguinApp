package pe.edu.upc.bodeguin.data.repository

import android.util.Log
import com.google.gson.Gson
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.model.request.AuthRequest
import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse
import pe.edu.upc.bodeguin.data.network.service.AuthService
import retrofit2.*

class AuthRepository(var service: AuthService) {
    var objAuthenticate = AuthResponse()
    
    fun authenticate(authRequest: AuthRequest): AuthResponse {
        service.authenticate(authRequest).enqueue(object: Callback<AuthResponse> {
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("error", "conexion")
            }

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonString = Gson().toJson(responseBody)
                    val objAuth: AuthResponse =
                        Gson().fromJson(jsonString, AuthResponse::class.java)
                    objAuthenticate = objAuth
                }
            }
        })
        return objAuthenticate
    }
}