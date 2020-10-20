package pe.edu.upc.bodeguin.ui.viewModel.authentication

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.request.AuthRequest
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
    val dni: String = "-"
    val direction: String = "-"

    var mapper: Mapper = Mapper()
    var authListener: AuthListener? = null
    var registerListener: RegisterListener? = null

    fun authenticate(view: View) {
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure(getApplication<Application>().resources.getString(R.string.empty_credentials))
        } else {
            val authenticateRequest = AuthRequest(email!!, password!!)

            Coroutines.main {
                try {
                    val authResponse = repository.authenticate(authenticateRequest)
                    authResponse.let {
                        val user = mapper.authResponseToModel(authResponse)
                        repository.insertUser(user)
                        authListener?.onSuccess(authResponse)
                        //return@main
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
            val signUpRequest = SignUpRequest(0, email!!, password!!, name!!, firstLastName!!, secondLastName!!, direction, dni, enable = true, adm = false)
            Coroutines.main {
                try {
                    val signUpResponse = repository.createUser(signUpRequest)
                    signUpResponse.let {
                        val authRequest = AuthRequest(signUpRequest.correo!!, signUpRequest.password!!)
                        val authResponse = repository.authenticate(authRequest)
                        authResponse.let {
                            val user = mapper.authResponseToModel(authResponse)
                            repository.insertUser(user)
                            registerListener?.success(user)
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