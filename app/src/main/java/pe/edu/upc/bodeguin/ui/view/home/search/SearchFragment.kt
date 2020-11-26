package pe.edu.upc.bodeguin.ui.view.home.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.data.ProductData
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModel
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModelFactory
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class SearchFragment : Fragment(), ProductListener, KodeinAware {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter
    override val kodein by kodein()
    private val factory: ProductViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)

        val sharedPreferences = activity!!.getSharedPreferences("localData", 0)
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
        val manager = GridLayoutManager(activity, 2)
        rvSearchProducts.layoutManager = manager
    }

    override fun onSuccess(products: List<ProductData>) {
        activity!!.lottieLoadingMain.hide()
        activity!!.lottieLoadingMain.cancelAnimation()
        productAdapter = ProductAdapter(products, context!!)
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