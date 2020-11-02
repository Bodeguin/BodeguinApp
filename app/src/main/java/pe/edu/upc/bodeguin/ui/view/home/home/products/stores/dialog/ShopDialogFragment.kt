package pe.edu.upc.bodeguin.ui.view.home.home.products.stores.dialog

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_shop_dialog.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.persistance.model.Cart
import pe.edu.upc.bodeguin.data.repository.CartRepository
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModel
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModelFactory

class ShopDialogFragment : DialogFragment() {

    private var number = 1
    private lateinit var imageView: ImageView
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private var productName: String = ""
    private var storeName: String = ""
    private var idInventory: Int = 0
    private var maxQuantity: Int = 0
    private var price: Double = 0.0
    private var priceString: String = ""
    private var measureUnit: String = ""
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(activity!!.applicationContext)
        val api = ApiGateway
        val db = AppDatabase.getInstance(activity!!.applicationContext)
        val repository = CartRepository(networkConnectionInterceptor, api, db)
        val factory = CartViewModelFactory(activity!!.application, repository)
        cartViewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shop_dialog, container, false)
        imageView = view.findViewById(R.id.ivProductUrl)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments != null){
            val myArgs = arguments
            setVariables(myArgs)
        }
        tvTotalProduct.text = number.toString()
        bCloseShop.setOnClickListener {
            dismiss()
        }
        bRest.setOnClickListener {
            if (number > 1){
                number -= 1
                tvTotalProduct.text = number.toString()
            }
        }
        bAdd.setOnClickListener {
            if (number < maxQuantity) {
                number += 1
                tvTotalProduct.text = number.toString()
            }
        }
        addCart.setOnClickListener {
            saveProduct()
            dismiss()
        }
        bShare.setOnClickListener {
            val sharedIntent = Intent()
            shareData(sharedIntent)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun shareData(sharedIntent: Intent) {
        sharedIntent.action = Intent.ACTION_SEND
        sharedIntent.putExtra(Intent.EXTRA_TEXT,
            "$storeName - ${resources.getString(R.string.good_price)} $productName (${resources.getString(R.string.currency)}$priceString): https://www.google.com/maps/place/$lat,$lng"
        )
        sharedIntent.type = "text/plain"
        startActivity(sharedIntent)
    }

    private fun setVariables(myArgs: Bundle?) {
        idInventory = myArgs!!.getInt("idInventory")
        productName = myArgs.getString("productName")!!
        tvProductName.text = productName
        storeName = myArgs.getString("storeName")!!
        tvStoreName.text = storeName
        maxQuantity = myArgs.getInt("maxQuantity")
        priceString = myArgs.getString("price")!!
        tvPriceProduct.text = priceString
        price = priceString.toDouble()
        measureUnit = myArgs.getString("measureUnit")!!
        tvMeasureUnit.text = measureUnit
        lat = myArgs.getDouble("latitude")
        lng = myArgs.getDouble("longitude")
        Picasso.get().load(myArgs.getString("urlProduct")).into(imageView)
    }

    private fun saveProduct(){
        val total = number * priceString.toDouble()
        val cart = Cart(idInventory, number, priceString, total, measureUnit, storeName, "", productName)
        cartViewModel.insertData(cart)
    }
}