package pe.edu.upc.bodeguin.util

import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse
import pe.edu.upc.bodeguin.data.persistance.model.User

class Mapper() {
    fun authResponseToModel(authResponse: AuthResponse) : User {
        return User(
            authResponse.id,
            authResponse.correo,
            authResponse.password,
            authResponse.nombre,
            authResponse.apellidoPaterno,
            authResponse.apellidoMaterno,
            authResponse.direccion,
            authResponse.dni,
            authResponse.enable,
            authResponse.adm
        )
    }
}
