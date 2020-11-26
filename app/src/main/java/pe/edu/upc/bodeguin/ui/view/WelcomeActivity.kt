package pe.edu.upc.bodeguin.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kotlinx.android.synthetic.main.activity_welcome.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.ui.view.authentication.LoginActivity
import pe.edu.upc.bodeguin.ui.view.home.MainActivity

class WelcomeActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var delay: Long = 3000

    private val mRunnable: Runnable = Runnable {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        mDelayHandler = Handler(Looper.getMainLooper())
        icon_done.playAnimation()
        mDelayHandler!!.postDelayed(mRunnable, delay)
    }

    override fun onDestroy() {
        icon_done.cancelAnimation()
        mDelayHandler!!.removeCallbacks(mRunnable)
        super.onDestroy()
    }
}