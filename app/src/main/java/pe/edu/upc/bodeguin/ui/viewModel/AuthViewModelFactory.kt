package pe.edu.upc.bodeguin.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.edu.upc.bodeguin.data.repository.AuthRepository

class AuthViewModelFactory(
    private val application: Application,
    private val repository: AuthRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(application, repository) as T
    }
}