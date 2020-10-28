package pe.edu.upc.bodeguin.ui.view.authentication

import pe.edu.upc.bodeguin.data.network.model.response.LoginResponse

interface RegisterListener {
    fun start()
    fun success(user: LoginResponse)
    fun fail(message: String)
}