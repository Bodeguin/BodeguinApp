package pe.edu.upc.bodeguin.ui.view.authentication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.persistance.model.User
import pe.edu.upc.bodeguin.data.repository.UserRepository
import pe.edu.upc.bodeguin.databinding.ActivityRegisterBinding
import pe.edu.upc.bodeguin.ui.view.home.MainActivity
import pe.edu.upc.bodeguin.ui.viewModel.authentication.AuthViewModel
import pe.edu.upc.bodeguin.ui.viewModel.authentication.AuthViewModelFactory
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class RegisterActivity : AppCompatActivity(), RegisterListener {

    private var backPress = 0
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        toolbar = findViewById(R.id.initialToolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.registry_account)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = ApiGateway
        val db = AppDatabase.getInstance(this)
        val repository = UserRepository(networkConnectionInterceptor, api, db)
        val factory = AuthViewModelFactory(application, repository)

        val authViewModel = ViewModelProvider(this,
            factory).get(AuthViewModel::class.java)

        authViewModel.registerListener = this
        binding.viewModel = authViewModel
    }

    private fun saveData(id: String) {
        val editor: SharedPreferences.Editor = getSharedPreferences("data", 0).edit()
        editor.putString("token", id)
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

    override fun success(user: User) {
        lottieLoadingRegister.hide()
        lottieLoadingRegister.cancelAnimation()
        if (user.id == 0) rootLayout.snackBar(resources.getString(R.string.wrong_credentials))
        else {
            saveData(user.id.toString())
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

    override fun fail(message: String) {
        lottieLoadingRegister.hide()
        lottieLoadingRegister.cancelAnimation()
        clRegister.snackBar(message)
    }
}