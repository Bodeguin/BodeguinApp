package pe.edu.upc.bodeguin.data.repository

import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.api.SafeApiRequest
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.network.model.request.LoginRequest
import pe.edu.upc.bodeguin.data.network.model.request.SignUpRequest
import pe.edu.upc.bodeguin.data.network.model.request.UpdateRequest
import pe.edu.upc.bodeguin.data.network.model.response.*
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.persistance.model.User

class UserRepository(
    private val networkConnectionInterceptor: NetworkConnectionInterceptor,
    private val api: ApiGateway,
    private val db: AppDatabase
) : SafeApiRequest() {
    suspend fun authenticate(loginRequest: LoginRequest) : LoginResponse {
        return apiRequest { api.instance(networkConnectionInterceptor).authenticate(loginRequest) }
    }
    suspend fun createUser(signUpRequest: SignUpRequest) : SignUpResponse {
        return apiRequest { api.instance(networkConnectionInterceptor).signUp(signUpRequest) }
    }
    suspend fun getUserApi(token: String, id: Int) : UserResponse {
        return apiRequest { api.instance(networkConnectionInterceptor).getUser(token, id) }
    }
    suspend fun updateUserApi(token: String, id: Int, updateRequest: UpdateRequest) : UserResponse {
        return apiRequest { api.instance(networkConnectionInterceptor).updateUserApi(token, id, updateRequest) }
    }
    suspend fun insertUser(user: User) = db.userDao().insert(user)
    fun getUser() = db.userDao().getUser()
    fun deleteUser() = db.userDao().deleteAll()
    fun getUserEdit() = db.userDao().getUserEdit()
}