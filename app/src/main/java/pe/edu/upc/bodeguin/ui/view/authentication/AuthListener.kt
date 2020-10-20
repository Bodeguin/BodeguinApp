package pe.edu.upc.bodeguin.ui.view.authentication

import androidx.lifecycle.LiveData
import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse

interface AuthListener {
    fun onStarted()
    fun onSuccess(loginResponse: AuthResponse)
    fun onFailure(message: String)
}