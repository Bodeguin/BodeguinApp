package pe.edu.upc.bodeguin.ui.viewModel.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.edu.upc.bodeguin.data.repository.UserRepository

class UserViewModelFactory(
    private val application: Application,
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(
            application,
            repository
        ) as T
    }
}