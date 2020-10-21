package pe.edu.upc.bodeguin.ui.viewModel.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.bodeguin.data.persistance.model.User
import pe.edu.upc.bodeguin.data.repository.UserRepository
import pe.edu.upc.bodeguin.ui.view.home.profile.UserListener
import pe.edu.upc.bodeguin.util.Coroutines

class UserViewModel(
    application: Application?,
    private val repository: UserRepository?
) : AndroidViewModel(application!!) {
    var userListener: UserListener? = null

    var user = repository!!.getUser()

    fun deleteUser() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository!!.deleteUser()
            userListener?.onSuccess()
        } catch (e: Exception) {
            userListener?.onFailure(e.message!!)
        }
    }

    fun getLoggedUserDetail(): LiveData<User> {
        return repository!!.getUser()
    }
}