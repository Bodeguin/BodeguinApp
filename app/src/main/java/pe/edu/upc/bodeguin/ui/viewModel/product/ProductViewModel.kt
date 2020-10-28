package pe.edu.upc.bodeguin.ui.viewModel.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.repository.ProductRepository
import pe.edu.upc.bodeguin.ui.view.home.search.ProductListener
import pe.edu.upc.bodeguin.util.ApiException
import pe.edu.upc.bodeguin.util.Coroutines
import pe.edu.upc.bodeguin.util.NoInternetException

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
            try {
                val products = repository!!.getProductApi(token, query)
                if (products.valid) {
                    products.let{
                        productListener?.onSuccess(products.data)
                    }
                } else {
                    productListener?.onFailure(getApplication<Application>().resources.getString(R.string.error_getproducts))
                }
            } catch (e: ApiException) {
                productListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                productListener?.onFailure(e.message!!)
            }
        }
    }
}