package pe.edu.upc.bodeguin.ui.view.home.search

import pe.edu.upc.bodeguin.data.network.model.response.data.ProductData

interface ProductListener {
    fun onStarted()
    fun onSuccess(products: List<ProductData>)
    fun onFailure(message: String)
}