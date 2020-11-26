package pe.edu.upc.bodeguin.ui.view.home

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.persistance.model.User
import pe.edu.upc.bodeguin.ui.view.authentication.LoginActivity
import pe.edu.upc.bodeguin.ui.view.home.home.HomeFragment
import pe.edu.upc.bodeguin.ui.view.home.profile.ProfileFragment
import pe.edu.upc.bodeguin.ui.view.home.profile.UserListener
import pe.edu.upc.bodeguin.ui.view.home.search.SearchFragment
import pe.edu.upc.bodeguin.ui.view.home.shooping.ShoppingFragment
import pe.edu.upc.bodeguin.ui.view.home.store.MapsFragment
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModel
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModelFactory
import pe.edu.upc.bodeguin.ui.viewModel.profile.UserViewModel
import pe.edu.upc.bodeguin.ui.viewModel.profile.UserViewModelFactory
import pe.edu.upc.bodeguin.util.Coroutines
import pe.edu.upc.bodeguin.util.snackBar

class MainActivity : AppCompatActivity(),
    UserListener, KodeinAware {

    private lateinit var toolbar: Toolbar
    private var notificationId = 0

    private lateinit var userViewModel: UserViewModel
    private lateinit var cartViewModel: CartViewModel
    private var result: Boolean = false

    companion object {
        const val CHANNEL_ID = "pe.edu.upc.bodega.NOTIFICATION"
    }

    override val kodein by kodein()
    private val factory: UserViewModelFactory by instance()
    private val cartFactory: CartViewModelFactory by instance()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartViewModel = ViewModelProvider(this, cartFactory).get(CartViewModel::class.java)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        userViewModel.userListener = this

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
        supportActionBar?.setIcon(resources.getDrawable(R.drawable.ic_logo, null))

        result = intent.getBooleanExtra("SUCCESS_PAY", false)
        if (result) {
            flContent.snackBar(resources.getString(R.string.register_sale))
        }
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btLogout -> {
                Coroutines.main {
                    cartViewModel.deleteAll()
                    userViewModel.deleteUser()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }

    private fun logOut() {
        val editor: SharedPreferences.Editor = getSharedPreferences("localData", 0).edit()
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
            R.id.iStore -> MapsFragment()
            R.id.iShopping -> ShoppingFragment()
            R.id.iProfile -> ProfileFragment()
            R.id.iSearch -> SearchFragment()
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
        val builder = NotificationCompat.Builder(this,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(resources.getString(R.string.title_notification))
            .setContentText(resources.getString(R.string.return_notification))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
            notificationId++
        }
    }

    override fun onSuccess() {
        appNotify()
        logOut()
    }

    override fun onSuccessUpdate(user: User) {
        TODO("Not yet implemented")
    }

    override fun onFailure(message: String) {
        flContent.snackBar(message)
    }

    override fun onClose() {
        TODO("Not yet implemented")
    }
}