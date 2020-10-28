package pe.edu.upc.bodeguin.ui.view.home.home

import pe.edu.upc.bodeguin.data.network.model.response.data.CategoryData

interface HomeListener {
    fun onSuccess(categories: List<CategoryData>)
    fun onFailure(message: String)
    fun onStarted()
}