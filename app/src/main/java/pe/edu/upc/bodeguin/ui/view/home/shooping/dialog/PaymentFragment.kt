package pe.edu.upc.bodeguin.ui.view.home.shooping.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_shopping.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.persistance.model.Cart
import pe.edu.upc.bodeguin.data.repository.CartRepository
import pe.edu.upc.bodeguin.ui.view.home.MainActivity
import pe.edu.upc.bodeguin.ui.view.home.shooping.CartListener
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModel
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModelFactory
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class PaymentFragment : DialogFragment() {

    private lateinit var cartViewModel: CartViewModel
    private var selectIdButton: Int = 1
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(activity!!.applicationContext)
        val api = ApiGateway
        val db = AppDatabase.getInstance(activity!!.applicationContext)
        val repository = CartRepository(networkConnectionInterceptor, api, db)
        val factory = CartViewModelFactory(activity!!.application, repository)
        cartViewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)

        val sharedPreferences = activity!!.getSharedPreferences("data", 0)
        val token = sharedPreferences.getString("token", "")

        cartViewModel.setToken(token.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bClosePayment.setOnClickListener {
            dismiss()
        }
        bBuy.setOnClickListener {
            val idButton = rgPaymentMethod.checkedRadioButtonId
            val button = view.findViewById<RadioButton>(idButton)
            selectIdButton = if (button.text == view.resources.getString(R.string.credit_card)){
                2
            } else {
                1
            }
            //cartViewModel.buyShop()
            dismiss()
        }
    }
}