package pe.edu.upc.bodeguin.ui.view.home.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.network.model.response.data.ProductData
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.repository.ProductRepository
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModel
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModelFactory
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class SearchFragment : Fragment(), ProductListener {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(activity!!.applicationContext)
        val api = ApiGateway
        val db = AppDatabase.getInstance(activity!!.applicationContext)
        val repository = ProductRepository(networkConnectionInterceptor, api, db)
        val factory = ProductViewModelFactory(activity!!.application, repository)
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)

        val sharedPreferences = activity!!.getSharedPreferences("data", 0)
        val token = sharedPreferences.getString("token", "")

        productViewModel.setToken(token.toString())
        productViewModel.productListener = this

        productViewModel.getProducts("")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchView = activity!!.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()){
                    productViewModel.getProducts(newText)
                    rvSearchProducts.adapter!!.notifyDataSetChanged()
                } else {
                    productViewModel.getProducts("")
                    rvSearchProducts.adapter!!.notifyDataSetChanged()
                }
                return true
            }
        })
        rvSearchProducts.layoutManager = LinearLayoutManager(context)
    }

    override fun onSuccess(products: List<ProductData>) {
        activity!!.lottieLoadingMain.hide()
        activity!!.lottieLoadingMain.cancelAnimation()
        productAdapter = ProductAdapter(products)
        rvSearchProducts.adapter = productAdapter
    }

    override fun onFailure(message: String) {
        activity!!.lottieLoadingMain.hide()
        activity!!.lottieLoadingMain.cancelAnimation()
        clSearch.snackBar(message)
    }

    override fun onStarted() {
        activity!!.lottieLoadingMain.show()
        activity!!.lottieLoadingMain.playAnimation()
    }
}