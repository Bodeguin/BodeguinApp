package pe.edu.upc.bodeguin.ui.viewModel.category

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.edu.upc.bodeguin.data.repository.CategoryRepository
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModel

@Suppress("UNCHECKED_CAST")
class CategoryViewModelFactory(
    private val application: Application,
    private val repository: CategoryRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(
            application,
            repository
        ) as T
    }
}