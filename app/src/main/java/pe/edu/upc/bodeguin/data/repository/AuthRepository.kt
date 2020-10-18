package pe.edu.upc.bodeguin.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.api.SafeApiRequest
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.network.model.request.AuthRequest
import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse
import pe.edu.upc.bodeguin.data.network.service.AuthService
import retrofit2.*

class AuthRepository(
    private val networkConnectionInterceptor: NetworkConnectionInterceptor,
    private val api: ApiGateway
) : SafeApiRequest() {
    suspend fun authenticate(authRequest: AuthRequest) : AuthResponse {
        return apiRequest { api.instance(networkConnectionInterceptor).authenticate(authRequest) }
    }
}