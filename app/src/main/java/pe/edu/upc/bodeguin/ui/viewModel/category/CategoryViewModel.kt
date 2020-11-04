package pe.edu.upc.bodeguin.ui.viewModel.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.repository.CategoryRepository
import pe.edu.upc.bodeguin.ui.view.home.home.HomeListener
import pe.edu.upc.bodeguin.util.ApiException
import pe.edu.upc.bodeguin.util.Coroutines
import pe.edu.upc.bodeguin.util.NoInternetException

class CategoryViewModel(
    application: Application?,
    private val repository: CategoryRepository
) : AndroidViewModel(application!!) {

    var token: String = "Bearer "
    var homeListener: HomeListener? = null

    fun setToken(userToken: String) = viewModelScope.launch(Dispatchers.IO) {
        token = "Bearer $userToken"
    }

    fun getCategories(token: String) {
        homeListener?.onStarted()
        Coroutines.main {
            try {
                val categories = repository!!.getCategoriesApi(token)
                if (categories.valid) {
                    categories.let {
                        homeListener?.onSuccess(categories.data)
                    }
                } else {
                    homeListener?.onFailure(getApplication<Application>().resources.getString(R.string.error_getcategories))
                }
            } catch (e: ApiException) {
                homeListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                homeListener?.onFailure(e.message!!)
            }
        }
    }
}