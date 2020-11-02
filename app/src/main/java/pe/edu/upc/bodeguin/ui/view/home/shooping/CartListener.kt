package pe.edu.upc.bodeguin.ui.view.home.shooping

import pe.edu.upc.bodeguin.data.persistance.model.Cart

interface CartListener {
    fun onSuccess(carts: List<Cart>)
    fun onStarted()
    fun onFailure(message: String)
}