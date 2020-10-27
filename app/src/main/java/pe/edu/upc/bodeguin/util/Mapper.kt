package pe.edu.upc.bodeguin.util

import pe.edu.upc.bodeguin.data.network.model.request.SignUpRequest
import pe.edu.upc.bodeguin.data.network.model.request.UpdateRequest
import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse
import pe.edu.upc.bodeguin.data.network.model.response.LoginResponse
import pe.edu.upc.bodeguin.data.network.model.response.UserResponse
import pe.edu.upc.bodeguin.data.persistance.model.User
import kotlin.math.log

class Mapper() {
    fun loginResponseToModel(loginResponse: LoginResponse) : User {
        return User(
            loginResponse.data.id,
            loginResponse.data.email,
            loginResponse.data.password,
            loginResponse.data.name,
            loginResponse.data.firstLastName,
            loginResponse.data.secondLastName,
            loginResponse.data.direction,
            loginResponse.data.dni
        )
    }
    fun userResponseToUpdateRequest(userResponse: UserResponse): UpdateRequest {
        return UpdateRequest(
            userResponse.data.name,
            userResponse.data.firstLastName,
            userResponse.data.secondLastName,
            userResponse.data.email,
            userResponse.data.password,
            userResponse.data.direction,
            userResponse.data.dni
        )
    }
    fun userResponseToModel(userResponse: UserResponse) : User {
        return User(
            userResponse.data.id,
            userResponse.data.email,
            userResponse.data.password,
            userResponse.data.name,
            userResponse.data.firstLastName,
            userResponse.data.secondLastName,
            userResponse.data.direction,
            userResponse.data.dni
        )
    }
}
