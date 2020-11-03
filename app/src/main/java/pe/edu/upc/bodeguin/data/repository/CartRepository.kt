package pe.edu.upc.bodeguin.data.repository

import androidx.lifecycle.LiveData
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.api.SafeApiRequest
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.network.model.request.VoucherRequest
import pe.edu.upc.bodeguin.data.network.model.response.ShopCartResponse
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.persistance.model.Cart

class CartRepository(
    private val networkConnectionInterceptor: NetworkConnectionInterceptor,
    private val api: ApiGateway,
    private val db: AppDatabase
) : SafeApiRequest() {
    var carts: LiveData<List<Cart>> = db.cartDao().getShoppingCart()
    var totalPriceCart: LiveData<Double> = db.cartDao().getTotalPrice()
    suspend fun insert(cart: Cart) = db.cartDao().insert(cart)
    fun getShoppingCart() = db.cartDao().getCarts()
    fun getTotalPrice() = db.cartDao().getTotalPrice()
    fun getTotalItemsCarts() = db.cartDao().getTotalItemsCarts()
    fun getById(id: Int) = db.cartDao().getById(id)
    fun delete(cart: Cart) = db.cartDao().delete(cart)
    fun deleteAll() = db.cartDao().deleteAll()
    suspend fun insertShopBuy(token: String, voucherRequest: VoucherRequest): ShopCartResponse {
        return apiRequest { api.instance(networkConnectionInterceptor).buyShopCart(token, voucherRequest) }
    }
}