package pe.edu.upc.bodeguin.ui.viewModel.cart

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.bodeguin.data.persistance.model.Cart
import pe.edu.upc.bodeguin.data.repository.CartRepository
import pe.edu.upc.bodeguin.ui.view.home.shooping.CartListener
import pe.edu.upc.bodeguin.util.ApiException
import pe.edu.upc.bodeguin.util.Coroutines
import pe.edu.upc.bodeguin.util.NoInternetException
import kotlin.jvm.internal.Ref
import kotlin.math.round

class CartViewModel(
    application: Application?,
    private val repository: CartRepository
) : AndroidViewModel(application!!) {

    var token: String = "Bearer "
    private var cartListener: CartListener? = null
    var totalPriceCart: LiveData<Double> = repository.totalPriceCart
    var carts: LiveData<List<Cart>> = repository.carts

    fun setToken(userToken: String) = viewModelScope.launch(Dispatchers.IO) {
        token = "Bearer $userToken"
    }

    fun insertData(cart: Cart) = viewModelScope.launch(Dispatchers.IO)  {
        repository.insert(cart)
    }

    fun getCart() = viewModelScope.launch(Dispatchers.IO) {
        try {
            cartListener?.onStarted()
        } catch (e: ApiException) {
            Log.d("data", e.message!!)
        }
    }

    fun delete(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val cart = repository.getById(id)
        repository.delete(cart)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun buyCart() {
        Coroutines.main {
            for (item in carts.value!!) {
                Log.d("data", "dato: " + item.id)
            }
        }
    }
}