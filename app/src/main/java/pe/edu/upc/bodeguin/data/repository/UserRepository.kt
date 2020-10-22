package pe.edu.upc.bodeguin.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.api.SafeApiRequest
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.network.model.request.AuthRequest
import pe.edu.upc.bodeguin.data.network.model.request.SignUpRequest
import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse
import pe.edu.upc.bodeguin.data.network.service.AuthService
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.persistance.model.User
import retrofit2.*

class UserRepository(
    private val networkConnectionInterceptor: NetworkConnectionInterceptor,
    private val api: ApiGateway,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun authenticate(authRequest: AuthRequest) : AuthResponse {
        return apiRequest { api.instance(networkConnectionInterceptor).authenticate(authRequest) }
    }
    suspend fun createUser(signUpRequest: SignUpRequest) : AuthResponse {
        return apiRequest { api.instance(networkConnectionInterceptor).signUp(signUpRequest) }
    }
    suspend fun getUserApi(id: Int) : AuthResponse {
        return apiRequest { api.instance(networkConnectionInterceptor).getUser(id) }
    }
    suspend fun updateUserApi(id: Int, signUpRequest: SignUpRequest) : AuthResponse {
        return apiRequest { api.instance(networkConnectionInterceptor).updateUserApi(id, signUpRequest) }
    }
    suspend fun insertUser(user: User) = db.userDao().insert(user)
    fun getUser() = db.userDao().getUser()
    fun deleteUser() = db.userDao().deleteAll()
}