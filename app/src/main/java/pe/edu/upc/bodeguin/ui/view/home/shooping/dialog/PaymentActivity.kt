package pe.edu.upc.bodeguin.ui.view.home.shooping.dialog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.fragment_home.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.persistance.model.Cart
import pe.edu.upc.bodeguin.data.repository.CartRepository
import pe.edu.upc.bodeguin.ui.view.home.MainActivity
import pe.edu.upc.bodeguin.ui.view.home.shooping.CartListener
import pe.edu.upc.bodeguin.ui.view.home.shooping.ShoppingFragment
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModel
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModelFactory
import pe.edu.upc.bodeguin.util.Coroutines
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class PaymentActivity : AppCompatActivity(), CartListener {

    private lateinit var cartViewModel: CartViewModel
    private var selectIdButton: Int = 1
    private var userId: Int = 0
    private var token: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(applicationContext)
        val api = ApiGateway
        val db = AppDatabase.getInstance(applicationContext)
        val repository = CartRepository(networkConnectionInterceptor, api, db)
        val factory = CartViewModelFactory(application, repository)
        cartViewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)

        val sharedPreferences = getSharedPreferences("data", 0)
        token = sharedPreferences.getString("token", "")
        userId = sharedPreferences.getString("id", "")!!.toInt()

        cartViewModel.setToken(token!!)
        cartViewModel.cartListener = this

        bBuy.setOnClickListener {
            val idButton = rgPaymentMethod.checkedRadioButtonId
            val button = findViewById<RadioButton>(idButton)
            selectIdButton = if (button.text == resources.getString(R.string.credit_card)){
                2
            } else {
                1
            }
            Coroutines.main {
                cartViewModel.buyShop(userId, selectIdButton)
            }
        }

        bClosePayment.setOnClickListener {
            finish()
        }
    }

    override fun onSuccess(carts: List<Cart>) {
        TODO("Not yet implemented")
    }

    override fun onStarted() {
        lottieLoadingPayment.show()
        lottieLoadingPayment.playAnimation()
    }

    override fun onSuccessBuy() {
        lottieLoadingPayment.hide()
        lottieLoadingPayment.cancelAnimation()
        Coroutines.main {
            cartViewModel.deleteAll()
        }
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("SUCCESS_PAY", true)
        startActivity(intent)
    }

    override fun onFailure(message: String) {
        lottieLoadingPayment.hide()
        lottieLoadingPayment.cancelAnimation()
        clPaymentMethod.snackBar(message)
    }
}