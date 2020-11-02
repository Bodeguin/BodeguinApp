package pe.edu.upc.bodeguin.ui.viewModel.cart

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.edu.upc.bodeguin.data.repository.CartRepository

@Suppress("UNCHECKED_CAST")
class CartViewModelFactory (
    private val application: Application,
    private val repository: CartRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CartViewModel(
            application,
            repository
        ) as T
    }
}