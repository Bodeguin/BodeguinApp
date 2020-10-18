package pe.edu.upc.bodeguin.ui.view.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.network.model.request.AuthRequest
import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse
import pe.edu.upc.bodeguin.data.repository.AuthRepository
import pe.edu.upc.bodeguin.databinding.ActivityLoginBinding
import pe.edu.upc.bodeguin.ui.view.listeners.AuthListener
import pe.edu.upc.bodeguin.ui.viewModel.AuthViewModel
import pe.edu.upc.bodeguin.ui.viewModel.AuthViewModelFactory
import pe.edu.upc.bodeguin.util.snackbar
import pe.edu.upc.bodeguin.util.toast

class LoginActivity : AppCompatActivity(), AuthListener {

    private var backPress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences("data", 0)
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = ApiGateway
        val repository = AuthRepository(networkConnectionInterceptor, api)
        val factory = AuthViewModelFactory(application, repository)

        val authViewModel = ViewModelProvider(this,
            factory).get(AuthViewModel::class.java)

        //ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        authViewModel.authListener = this

        if (sharedPreferences.contains("token")) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        } else {
            val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
            // delete val authViewModel
            binding.viewModel = authViewModel
        }
    }

    fun showRegister(view: View) {
        startActivity(Intent(applicationContext, RegisterActivity::class.java))
    }

    override fun onBackPressed() {
        backPress += 1
        if (backPress > 1) {
            this.finishAffinity()
        } else {
            Toast.makeText(applicationContext, resources.getText(R.string.exit_text), Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveData(id: String) {
        val editor: SharedPreferences.Editor = getSharedPreferences("data", 0).edit()
        editor.putString("token", id)
        editor.apply()
    }

    override fun onStarted() {
        //toast("Login Started")
    }

    override fun onSuccess(loginResponse: AuthResponse) {
            if (loginResponse.id == 0) rootLayout.snackbar(resources.getString(R.string.wrong_credentials))
            else {
                saveData(loginResponse.id.toString())
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
    }

    override fun onFailure(message: String) {
        rootLayout.snackbar(message)
    }
}