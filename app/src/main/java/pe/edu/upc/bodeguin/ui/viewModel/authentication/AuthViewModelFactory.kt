package pe.edu.upc.bodeguin.ui.viewModel.authentication

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.edu.upc.bodeguin.data.repository.UserRepository

class AuthViewModelFactory(
    private val application: Application,
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(
            application,
            repository
        ) as T
    }
}