package pe.edu.upc.bodeguin.ui.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import pe.edu.upc.bodeguin.R

class LaunchActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var delay: Long = 2500

    private val mRunnable: Runnable = Runnable {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, delay)
    }

    override fun onDestroy() {
        if(mRunnable != null){
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}