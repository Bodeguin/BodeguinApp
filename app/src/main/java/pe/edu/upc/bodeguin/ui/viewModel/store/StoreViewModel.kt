package pe.edu.upc.bodeguin.ui.viewModel.store

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.GoogleMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.bodeguin.data.network.model.response.data.StoreData
import pe.edu.upc.bodeguin.data.persistance.model.User
import pe.edu.upc.bodeguin.data.repository.StoreRepository
import pe.edu.upc.bodeguin.data.repository.UserRepository
import pe.edu.upc.bodeguin.ui.view.home.store.StoreListener
import pe.edu.upc.bodeguin.util.Coroutines

class StoreViewModel(
    application: Application?,
    private val repository: StoreRepository?
) : AndroidViewModel(application!!)  {

    var token: String = "Bearer "
    var storeListener: StoreListener? = null

    fun setToken(userToken: String) = viewModelScope.launch(Dispatchers.IO) {
        token = "Bearer $userToken"
    }

    fun getStores(map: GoogleMap) {
        Coroutines.main {
            val stores = repository!!.getStoresApi(token)
            if (stores.valid) {
                stores.let{
                    storeListener?.onSuccess(stores.data)
                }
            } else {
                storeListener?.onFailure("Error to get stores")
            }
        }
    }
}