package pe.edu.upc.bodeguin.ui.view.authentication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.LoginResponse
import pe.edu.upc.bodeguin.databinding.ActivityLoginBinding
import pe.edu.upc.bodeguin.ui.view.home.MainActivity
import pe.edu.upc.bodeguin.ui.viewModel.authentication.AuthViewModel
import pe.edu.upc.bodeguin.ui.viewModel.authentication.AuthViewModelFactory
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {

    private var backPress = 0
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences("localData", 0)
        super.onCreate(savedInstanceState)

        val authViewModel = ViewModelProvider(this,
            factory).get(AuthViewModel::class.java)

        authViewModel.authListener = this

        if (sharedPreferences.contains("token")) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        } else {
            val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
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

    private fun saveData(token: String, id: String) {
        val editor: SharedPreferences.Editor = getSharedPreferences("localData", 0).edit()
        editor.putString("token", token)
        editor.putString("id", id)
        editor.apply()
    }

    override fun onStarted() {
        lottieLoading.show()
        lottieLoading.playAnimation()
    }

    override fun onSuccess(loginResponse: LoginResponse) {
        lottieLoading.hide()
        lottieLoading.cancelAnimation()
        if (loginResponse.data.id == 0) rootLayout.snackBar(resources.getString(R.string.wrong_credentials))
        else {
            saveData(loginResponse.data.token.toString(), loginResponse.data.id.toString())
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

    override fun onFailure(message: String) {
        lottieLoading.hide()
        lottieLoading.cancelAnimation()
        rootLayout.snackBar(message)
    }
}