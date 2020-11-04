package pe.edu.upc.bodeguin.ui.viewModel.cart

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.request.DetailRequest
import pe.edu.upc.bodeguin.data.network.model.request.VoucherRequest
import pe.edu.upc.bodeguin.data.persistance.model.Cart
import pe.edu.upc.bodeguin.data.repository.CartRepository
import pe.edu.upc.bodeguin.ui.view.home.shooping.CartListener
import pe.edu.upc.bodeguin.util.ApiException
import pe.edu.upc.bodeguin.util.Coroutines

class CartViewModel(
    application: Application?,
    private val repository: CartRepository
) : AndroidViewModel(application!!) {

    var token: String = "Bearer "
    var cartListener: CartListener? = null
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

    fun buyShop(userId: Int, paymentId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val cartsDetail = repository.getShoppingCart()
        val voucherRequest = VoucherRequest(paymentId, userId, emptyList())
        for (item in cartsDetail) {
            val detailRequest = DetailRequest(item.price!!.toDouble(), item.quantity!!, item.id!!)
            val result = voucherRequest.detail.plusElement(detailRequest)
            voucherRequest.detail = result
        }
        Coroutines.main {
            cartListener?.onStarted()
            val response = repository.insertShopBuy(token, voucherRequest)
            if(response.valid){
                cartListener?.onSuccessBuy()
            } else {
                cartListener?.onFailure(getApplication<Application>().resources.getString(R.string.out_stock) + ": " + response.data)
            }
        }
    }
}