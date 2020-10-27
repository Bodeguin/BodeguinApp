package pe.edu.upc.bodeguin.ui.viewModel.store

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.edu.upc.bodeguin.data.repository.StoreRepository

@Suppress("UNCHECKED_CAST")
class StoreViewModelFactory(
    private val application: Application,
    private val repository: StoreRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StoreViewModel(
            application,
            repository
        ) as T
    }
}