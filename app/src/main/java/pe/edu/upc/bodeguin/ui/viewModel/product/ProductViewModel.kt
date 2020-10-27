package pe.edu.upc.bodeguin.ui.viewModel.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.bodeguin.data.repository.ProductRepository
import pe.edu.upc.bodeguin.ui.view.home.search.ProductListener
import pe.edu.upc.bodeguin.util.Coroutines

class ProductViewModel(
    application: Application?,
    private val repository: ProductRepository?
) : AndroidViewModel(application!!)  {

    var token: String = "Bearer "
    var productListener: ProductListener? = null

    fun setToken(userToken: String) = viewModelScope.launch(Dispatchers.IO) {
        token = "Bearer $userToken"
    }

    fun getProducts(query: String) {
        productListener?.onStarted()
        Coroutines.main {
            val products = repository!!.getProductApi(token, query)
            if (products.valid) {
                products.let{
                    productListener?.onSuccess(products.data)
                }
            } else {
                productListener?.onFailure("Fallo al cargar productos")
            }
        }
    }
}