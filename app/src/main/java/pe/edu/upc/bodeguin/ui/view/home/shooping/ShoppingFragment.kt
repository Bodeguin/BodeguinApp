package pe.edu.upc.bodeguin.ui.view.home.shooping

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.wajahatkarim3.roomexplorer.RoomExplorer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_shopping.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.persistance.model.Cart
import pe.edu.upc.bodeguin.data.repository.CartRepository
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModel
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModelFactory
import pe.edu.upc.bodeguin.util.ItemTouchHelperCallback
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class ShoppingFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private var swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF4343"))
    private lateinit var deleteIcon: Drawable

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
        cartViewModel.getCart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bTest.setOnClickListener {
            RoomExplorer.show(context, AppDatabase::class.java, "BodeguinDatabase")
        }
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
                var resultText = ""
                resultText = if (it == null){
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
            cartViewModel.buyCart()
        }
    }
}