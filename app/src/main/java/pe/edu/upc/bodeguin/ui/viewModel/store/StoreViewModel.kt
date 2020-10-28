package pe.edu.upc.bodeguin.ui.viewModel.store

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.GoogleMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.repository.StoreRepository
import pe.edu.upc.bodeguin.ui.view.home.store.StoreListener
import pe.edu.upc.bodeguin.util.ApiException
import pe.edu.upc.bodeguin.util.Coroutines
import pe.edu.upc.bodeguin.util.NoInternetException

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
            try {
                val stores = repository!!.getStoresApi(token)
                if (stores.valid) {
                    stores.let{
                        storeListener?.onSuccess(stores.data)
                    }
                } else {
                    storeListener?.onFailure(getApplication<Application>().resources.getString(R.string.error_getstore))
                }
            } catch (e: ApiException) {
                storeListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                storeListener?.onFailure(e.message!!)
            }

        }
    }
}