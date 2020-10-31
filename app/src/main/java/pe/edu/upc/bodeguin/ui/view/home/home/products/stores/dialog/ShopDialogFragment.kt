package pe.edu.upc.bodeguin.ui.view.home.home.products.stores.dialog

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_shop_dialog.*
import pe.edu.upc.bodeguin.R

class ShopDialogFragment : DialogFragment() {

    private var number = 0
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shop_dialog, container, false)
        imageView = view.findViewById(R.id.ivProductUrl)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var lat: Double = 0.0
        var lng: Double = 0.0
        var productName: String = ""
        var storeName: String = ""
        var idInventory: Int = 0
        var maxQuantity: Int = 0
        var price: Double = 0.0
        var priceString: String = ""
        if (arguments != null){
            val myArgs = arguments
            idInventory = myArgs!!.getInt("idInventory")
            productName = myArgs.getString("productName")!!
            tvProductName.text = productName
            storeName = myArgs.getString("storeName")!!
            tvStoreName.text = storeName
            maxQuantity = myArgs.getInt("maxQuantity")
            priceString = myArgs.getString("price")!!
            tvPriceProduct.text = priceString
            price = priceString.toDouble()
            tvMeasureUnit.text = myArgs.getString("measureUnit")
            lat = myArgs.getDouble("latitude")
            lng = myArgs.getDouble("longitude")
            Picasso.get().load(myArgs.getString("urlProduct")).into(imageView)
        }else {
            tvProductName.text = "Doesn't Come"
        }
        tvTotalProduct.text = number.toString()
        bCloseShop.setOnClickListener {
            dismiss()
        }
        bRest.setOnClickListener {
            if (number > 0){
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
            number += 5
            tvTotalProduct.text = number.toString()
        }
        bShare.setOnClickListener {
            val sharedIntent = Intent()
            sharedIntent.action = Intent.ACTION_SEND
            sharedIntent.putExtra(Intent.EXTRA_TEXT,
                "$storeName - ${resources.getString(R.string.good_price)} $productName (${resources.getString(R.string.currency)}$priceString): https://www.google.com/maps/place/$lat,$lng"
            )
            sharedIntent.type = "text/plain"
            startActivity(sharedIntent)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}