package pe.edu.upc.bodeguin.ui.view.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.ui.view.fragments.HomeFragment
import pe.edu.upc.bodeguin.ui.view.fragments.ProfileFragment
import pe.edu.upc.bodeguin.ui.view.fragments.ShoppingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private var notificationId = 0

    companion object {
        const val CHANNEL_ID = "pe.edu.upc.bodega.NOTIFICATION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateTo(bnHome.menu.findItem(R.id.iHome))
        bnHome.selectedItemId = R.id.iHome

        createNotificationChannel()

        bnHome.setOnNavigationItemSelectedListener { item ->
            navigateTo(item)
            true
        }

        toolbar = findViewById(R.id.toolbarHome)
        setSupportActionBar(toolbar)

        supportActionBar?.title = resources.getString(R.string.app_name)
        supportActionBar?.setIcon(resources.getDrawable(R.drawable.ic_logo))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btLogout -> {
                appNotify()
                logOut()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }

    private fun logOut() {
        val editor: SharedPreferences.Editor = getSharedPreferences("data", 0).edit()
        editor.remove("token")
        editor.apply()
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun navigateTo(item: MenuItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flContent, fragmentFor(item))
            .commit()
    }

    private fun fragmentFor(item: MenuItem): Fragment {
        return when (item.itemId) {
            R.id.iShopping -> ShoppingFragment()
            R.id.iHome -> HomeFragment()
            R.id.iProfile -> ProfileFragment()
            else -> HomeFragment()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Shared"
            val descriptionText = resources.getString(R.string.return_notification)
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun appNotify() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(resources.getString(R.string.title_notification))
            .setContentText(resources.getString(R.string.return_notification))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
            notificationId++
        }
    }
}