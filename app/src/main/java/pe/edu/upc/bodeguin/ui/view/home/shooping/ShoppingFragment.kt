package pe.edu.upc.bodeguin.ui.view.home.shooping

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_shopping.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.ui.view.home.shooping.dialog.PaymentActivity
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModel
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModelFactory
import pe.edu.upc.bodeguin.util.ItemTouchHelperCallback
import pe.edu.upc.bodeguin.util.snackBar

class ShoppingFragment : Fragment(), KodeinAware {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private var swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF4343"))
    private lateinit var deleteIcon: Drawable

    override val kodein by kodein()
    private val factory: CartViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartViewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)

        val sharedPreferences = activity!!.getSharedPreferences("localData", 0)
        val token = sharedPreferences.getString("token", "")

        cartViewModel.setToken(token.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalPrice = view.findViewById<TextView>(R.id.tvTotalPrice)
        totalPrice.text = resources.getString(R.string.no_price)
        cartAdapter = CartAdapter(context!!)
        rvCartProducts.adapter = cartAdapter
        rvCartProducts.layoutManager = LinearLayoutManager(context!!)
        deleteIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_delete)!!
        cartViewModel.carts.observe(activity!!, Observer { carts ->
            carts?.let {
                cartAdapter.setCarts(it)
            }
        })
        cartViewModel.totalPriceCart.observe(activity!!, Observer {result ->
            result.let {
                val resultText = if (it == null){
                    resources.getString(R.string.no_price)
                } else {
                    String.format("%.2f", it)
                }
                totalPrice.text = resultText
            }
        })

        val itemTouchHelperCallback = ItemTouchHelperCallback(0, ItemTouchHelper.LEFT, cartAdapter, cartViewModel, deleteIcon, swipeBackground)

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvCartProducts)
        bBuyNow.setOnClickListener {
            val cartSize = cartViewModel.carts.value!!.size
            if (cartSize <= 0) {
                activity!!.clShoppingCart.snackBar(resources.getString(R.string.no_items_cart))
            } else {
                startActivity(Intent(context, PaymentActivity::class.java))
            }
        }
    }
}