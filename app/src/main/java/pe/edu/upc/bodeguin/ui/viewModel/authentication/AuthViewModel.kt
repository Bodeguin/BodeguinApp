package pe.edu.upc.bodeguin.ui.viewModel.authentication

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.request.LoginRequest
import pe.edu.upc.bodeguin.data.network.model.request.SignUpRequest
import pe.edu.upc.bodeguin.data.repository.UserRepository
import pe.edu.upc.bodeguin.ui.view.authentication.AuthListener
import pe.edu.upc.bodeguin.ui.view.authentication.RegisterListener
import pe.edu.upc.bodeguin.util.ApiException
import pe.edu.upc.bodeguin.util.Coroutines
import pe.edu.upc.bodeguin.util.Mapper
import pe.edu.upc.bodeguin.util.NoInternetException

class AuthViewModel(
    application: Application,
    private val repository: UserRepository
) : AndroidViewModel(application) {

    var email: String? = null
    var password: String? = null
    var name: String? = null
    var firstLastName: String? = null
    var secondLastName: String? = null

    private var mapper: Mapper = Mapper()
    var authListener: AuthListener? = null
    var registerListener: RegisterListener? = null

    fun authenticate(view: View) {
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure(getApplication<Application>().resources.getString(R.string.empty_credentials))
        } else {
            val loginRequest = LoginRequest(email!!, password!!)
            Coroutines.main {
                try {
                    val authResponse = repository.authenticate(loginRequest)
                    authResponse.let {
                        if (authResponse.valid) {
                            val user = mapper.loginResponseToModel(authResponse)
                            repository.insertUser(user)
                            authListener?.onSuccess(authResponse)
                        } else  {
                            when (authResponse.errorCode) {
                                1 -> authListener?.onFailure("The user doesn't exist")
                                else -> authListener?.onFailure("wrong password")
                            }
                        }
                    }
                    authListener?.onFailure(getApplication<Application>().resources.getString(R.string.wrong_credentials))
                } catch (e: ApiException) {
                    authListener?.onFailure(getApplication<Application>().resources.getString(R.string.wrong_credentials))
                }
                catch (e: NoInternetException){
                    authListener?.onFailure(e.message!!)
                }

            }
        }
    }

    fun signUp(view: View) {
        registerListener?.start()
        if(email.isNullOrEmpty() || password.isNullOrEmpty() || name.isNullOrEmpty() || firstLastName.isNullOrEmpty() || secondLastName.isNullOrEmpty()) {
            registerListener?.fail(getApplication<Application>().resources.getString(R.string.empty_inputs))
        } else {
            val signUpRequest = SignUpRequest(email!!, password!!, name!!, firstLastName!!, secondLastName!!)
            Coroutines.main {
                try {
                    val signUpResponse = repository.createUser(signUpRequest)
                    signUpResponse.let {
                        if (signUpResponse.valid) {
                            val loginRequest = LoginRequest(signUpRequest.email!!, signUpRequest.password!!)
                            val authResponse = repository.authenticate(loginRequest)
                            authResponse.let {
                                val user = mapper.loginResponseToModel(authResponse)
                                repository.insertUser(user)
                                registerListener?.success(user)
                            }
                        } else {
                            when (signUpResponse.errorCode){
                                3 -> registerListener?.fail("The user already exist")
                                else -> registerListener?.fail(getApplication<Application>().resources.getString(R.string.login_error))
                            }
                        }
                        registerListener?.fail(getApplication<Application>().resources.getString(R.string.login_error))
                    }
                    registerListener?.fail(getApplication<Application>().resources.getString(R.string.register_error))
                } catch (e: ApiException) {
                    registerListener?.fail(getApplication<Application>().resources.getString(R.string.wrong_inputs))
                } catch (e: NoInternetException) {
                    registerListener?.fail(e.message!!)
                }
            }
        }
    }
}