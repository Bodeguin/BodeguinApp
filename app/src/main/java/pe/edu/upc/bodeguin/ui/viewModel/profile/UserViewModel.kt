package pe.edu.upc.bodeguin.ui.viewModel.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.bodeguin.data.network.model.request.SignUpRequest
import pe.edu.upc.bodeguin.data.persistance.model.User
import pe.edu.upc.bodeguin.data.repository.UserRepository
import pe.edu.upc.bodeguin.ui.view.home.profile.UserListener
import pe.edu.upc.bodeguin.util.ApiException
import pe.edu.upc.bodeguin.util.Coroutines
import pe.edu.upc.bodeguin.util.Mapper
import pe.edu.upc.bodeguin.util.NoInternetException

class UserViewModel(
    application: Application?,
    private val repository: UserRepository?
) : AndroidViewModel(application!!) {

    var mapper = Mapper()
    var user = repository!!.getUser()
    var userListener: UserListener? = null

    fun deleteUser() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository!!.deleteUser()
            userListener?.onSuccess()
        } catch (e: Exception) {
            userListener?.onFailure(e.message!!)
        }
    }

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository!!.deleteUser()
        repository.insertUser(user)
    }

    fun getLoggedUserDetail(): LiveData<User> {
        return repository!!.getUser()
    }

    fun create(id: Int, email: String) {
        if(email.isNullOrEmpty()){
            userListener?.onFailure("Campos Vacios")
        } else {
            Coroutines.main {
                try {
                    val response = repository!!.getUserApi(id)

                    val signUpRequest = SignUpRequest(
                        response.id,
                        email,
                        response.password,
                        response.nombre,
                        response.apellidoPaterno,
                        response.apellidoMaterno,
                        response.direccion,
                        response.dni,
                        response.enable,
                        response.adm
                    )

                    val signUpResponse = repository.updateUserApi(id, signUpRequest)
                    val user = mapper.authResponseToModel(signUpResponse)
                    try {
                        updateUser(user)
                        userListener?.onSuccessUpdate(user)
                        return@main
                    } catch (e: Exception) {
                        userListener?.onFailure(e.message!!)
                    }
                } catch (e: ApiException) {
                    userListener?.onFailure(e.message!!)
                } catch (e: NoInternetException){
                    userListener?.onFailure(e.message!!)
                }
            }
        }
    }
}