package pe.edu.upc.bodeguin.ui.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.request.AuthRequest
import pe.edu.upc.bodeguin.data.repository.AuthRepository
import pe.edu.upc.bodeguin.ui.view.listeners.AuthListener
import pe.edu.upc.bodeguin.util.ApiException
import pe.edu.upc.bodeguin.util.Coroutines
import pe.edu.upc.bodeguin.util.NoInternetException

class AuthViewModel(
    application: Application,
    private val repository: AuthRepository
) : AndroidViewModel(application) {

    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun authenticate(view: View) {
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure(getApplication<Application>().resources.getString(R.string.empty_credentials))
        } else {
            val authenticateRequest = AuthRequest(email!!, password!!)

            Coroutines.main {

                try {
                    val authResponse = repository.authenticate(authenticateRequest)
                    authResponse?.let {
                        authListener?.onSuccess(authResponse)
                        //return@main
                    }
                    authListener?.onFailure(getApplication<Application>().resources.getString(R.string.wrong_credentials))
                } catch (e: ApiException) {
                    authListener?.onFailure(e.message!!)
                }
                catch (e: NoInternetException){
                    authListener?.onFailure(e.message!!)
                }

            }
        }
    }
}