package pe.edu.upc.bodeguin.ui.view.authentication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_register.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.LoginResponse
import pe.edu.upc.bodeguin.databinding.ActivityRegisterBinding
import pe.edu.upc.bodeguin.ui.view.WelcomeActivity
import pe.edu.upc.bodeguin.ui.viewModel.authentication.AuthViewModel
import pe.edu.upc.bodeguin.ui.viewModel.authentication.AuthViewModelFactory
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class RegisterActivity : AppCompatActivity(), RegisterListener, KodeinAware {

    private var backPress = 0
    private lateinit var toolbar: Toolbar
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        toolbar = findViewById(R.id.initialToolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.registry_account)

        val authViewModel = ViewModelProvider(this,
            factory).get(AuthViewModel::class.java)

        authViewModel.registerListener = this
        binding.viewModel = authViewModel
    }

    private fun saveData(token: String, id: String) {
        val editor: SharedPreferences.Editor = getSharedPreferences("localData", 0).edit()
        editor.putString("token", token)
        editor.putString("id", id)
        editor.apply()
    }

    override fun onBackPressed() {
        backPress += 1
        if (backPress > 1) {
            this.finishAffinity()
        } else {
            Toast.makeText(applicationContext, resources.getText(R.string.exit_text), Toast.LENGTH_SHORT).show()
        }
    }

    override fun start() {
        lottieLoadingRegister.show()
        lottieLoadingRegister.playAnimation()
    }

    override fun success(user: LoginResponse) {
        lottieLoadingRegister.hide()
        lottieLoadingRegister.cancelAnimation()
        if (!user.valid) clRegister.snackBar(resources.getString(R.string.wrong_credentials))
        else {
            saveData(user.data.token.toString(), user.data.id.toString())
            startActivity(Intent(applicationContext, WelcomeActivity::class.java))
            finish()
        }
    }

    override fun fail(message: String) {
        lottieLoadingRegister.hide()
        lottieLoadingRegister.cancelAnimation()
        clRegister.snackBar(message)
    }
}