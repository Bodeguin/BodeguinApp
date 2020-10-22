package pe.edu.upc.bodeguin.ui.viewModel.profile

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.bodeguin.R
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
    var id: Int? = null

    // Personal Data
    var name: String? = null
    var firstName: String? = null
    var secondName: String? = null
    var dni: String? = null

    // Direction
    var direction: String? = null

    // Account Info
    var email: String? = null
    var password: String? = null

    fun deleteUser() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository!!.deleteUser()
            userListener?.onSuccess()
        } catch (e: Exception) {
            userListener?.onFailure(e.message!!)
        }
    }

    fun cloneUser(userId: String) = viewModelScope.launch(Dispatchers.IO) {
        val user = repository!!.getUserEdit()
        email = user.correo!!
        password = user.password!!
        name = user.nombre!!
        firstName = user.apellidoPaterno!!
        secondName = user.apellidoMaterno!!
        dni = user.dni!!
        direction = user.direccion!!
        id = userId.toInt()
    }

    private fun deleteUserEdit() = viewModelScope.launch(Dispatchers.IO) {
        repository!!.deleteUser()
    }

    private fun insertUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository!!.insertUser(user)
    }

    fun getLoggedUserDetail(): LiveData<User> {
        return repository!!.getUser()
    }

    fun close(view: View){
        userListener?.onClose()
    }

    fun updateUserAccount(view: View) {
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            userListener?.onFailure(getApplication<Application>().resources.getString(R.string.empty_inputs))
        } else {
            Coroutines.main {
                try {
                    val response = repository!!.getUserApi(id!!)
                    response.correo = email!!
                    response.password = password!!
                    val signUpRequest = mapper.authResponseToSignUpRequest(response)
                    val signUpResponse = repository.updateUserApi(id!!, signUpRequest)
                    val user = mapper.authResponseToModel(signUpResponse)
                    try {
                        deleteUserEdit()
                        try {
                            insertUser(user)
                            userListener?.onSuccessUpdate(user)
                        } catch (e: Exception){
                            userListener?.onFailure(e.message!!)
                        }
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

    fun updateUserDirection(view: View) {
        if(direction.isNullOrEmpty()){
            userListener?.onFailure(getApplication<Application>().resources.getString(R.string.empty_inputs))
        } else {
            Coroutines.main {
                try {
                    val response = repository!!.getUserApi(id!!)
                    response.direccion = direction!!
                    val signUpRequest = mapper.authResponseToSignUpRequest(response)
                    val signUpResponse = repository.updateUserApi(id!!, signUpRequest)
                    val user = mapper.authResponseToModel(signUpResponse)
                    try {
                        deleteUserEdit()
                        try {
                            insertUser(user)
                            userListener?.onSuccessUpdate(user)
                        } catch (e: Exception){
                            userListener?.onFailure(e.message!!)
                        }
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

    fun updateUserProfile(view: View) {
        if(name.isNullOrEmpty() || firstName.isNullOrEmpty() || secondName.isNullOrEmpty() || dni.isNullOrEmpty()){
            userListener?.onFailure(getApplication<Application>().resources.getString(R.string.empty_inputs))
        } else {
            Coroutines.main {
                try {
                    val response = repository!!.getUserApi(id!!)
                    response.nombre = name!!
                    response.apellidoPaterno = firstName!!
                    response.apellidoMaterno = secondName!!
                    response.dni = dni!!
                    val signUpRequest = mapper.authResponseToSignUpRequest(response)
                    val signUpResponse = repository.updateUserApi(id!!, signUpRequest)
                    val user = mapper.authResponseToModel(signUpResponse)
                    try {
                        deleteUserEdit()
                        try {
                            insertUser(user)
                            userListener?.onSuccessUpdate(user)
                        } catch (e: Exception){
                            userListener?.onFailure(e.message!!)
                        }
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