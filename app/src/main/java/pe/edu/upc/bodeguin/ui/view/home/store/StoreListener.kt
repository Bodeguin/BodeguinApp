package pe.edu.upc.bodeguin.ui.view.home.store

import pe.edu.upc.bodeguin.data.network.model.response.data.StoreData

interface StoreListener {
    fun onSuccess(stores: List<StoreData>)
    fun onFailure(message: String)
}