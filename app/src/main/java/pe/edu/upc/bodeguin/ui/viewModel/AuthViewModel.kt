package pe.edu.upc.bodeguin.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.model.request.AuthRequest
import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse
import pe.edu.upc.bodeguin.data.repository.AuthRepository

class AuthViewModel(application: Application): AndroidViewModel(application) {
    private var repository: AuthRepository
    var user: AuthResponse

    init {
        user = AuthResponse()
        val service = ApiGateway.instance()
        repository = AuthRepository(service)
    }

    fun authenticate(authenticateRequest: AuthRequest): AuthResponse {
        return repository.authenticate(authenticateRequest)
    }
}