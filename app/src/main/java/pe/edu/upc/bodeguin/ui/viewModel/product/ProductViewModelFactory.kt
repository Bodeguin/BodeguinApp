package pe.edu.upc.bodeguin.ui.viewModel.product

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.edu.upc.bodeguin.data.repository.ProductRepository

@Suppress("UNCHECKED_CAST")
class ProductViewModelFactory(
    private val application: Application,
    private val repository: ProductRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductViewModel(
            application,
            repository
        ) as T
    }
}