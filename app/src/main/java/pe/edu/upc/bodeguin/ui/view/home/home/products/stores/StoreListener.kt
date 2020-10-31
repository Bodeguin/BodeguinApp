package pe.edu.upc.bodeguin.ui.view.home.home.products.stores

import pe.edu.upc.bodeguin.data.network.model.response.data.ProductStoreData

interface StoreListener {
    fun onStarted()
    fun onSuccess(products: List<ProductStoreData>)
    fun onFailure(message: String)
}