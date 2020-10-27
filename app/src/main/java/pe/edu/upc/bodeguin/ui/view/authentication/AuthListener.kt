package pe.edu.upc.bodeguin.ui.view.authentication

import pe.edu.upc.bodeguin.data.network.model.response.LoginResponse

interface AuthListener {
    fun onStarted()
    fun onSuccess(loginResponse: LoginResponse)
    fun onFailure(message: String)
}