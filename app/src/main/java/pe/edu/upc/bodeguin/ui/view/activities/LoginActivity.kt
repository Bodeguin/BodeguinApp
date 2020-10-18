package pe.edu.upc.bodeguin.ui.view.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.request.AuthRequest
import pe.edu.upc.bodeguin.data.network.model.response.AuthResponse
import pe.edu.upc.bodeguin.ui.viewModel.AuthViewModel

class LoginActivity : AppCompatActivity() {

    private var backPress = 0
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences("data", 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application))
            .get(AuthViewModel::class.java)

        if (sharedPreferences.contains("token")) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        } else {
            setContentView(R.layout.activity_login)
            val bnLogin = findViewById<MaterialButton>(R.id.bnLogin)
            bnLogin.setOnClickListener {
                val email = findViewById<TextInputEditText>(R.id.tiEmail).text
                val password = findViewById<TextInputEditText>(R.id.tiPassword).text
                val user = AuthRequest(email.toString(), password.toString())
                val response: AuthResponse = authViewModel.authenticate(user)
                if (response.id != 0) {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    saveData("12")
                    finish()
                }
            }
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
}