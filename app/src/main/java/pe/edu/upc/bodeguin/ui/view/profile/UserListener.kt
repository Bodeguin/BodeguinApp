package pe.edu.upc.bodeguin.ui.view.profile

import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse

interface UserListener {
    fun onSuccess()
    fun onFailure(message: String)
}