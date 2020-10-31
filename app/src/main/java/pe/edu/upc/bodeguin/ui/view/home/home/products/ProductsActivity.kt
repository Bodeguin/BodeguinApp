package pe.edu.upc.bodeguin.ui.view.home.home.products

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.fragment_search.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.network.model.response.data.ProductData
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.repository.ProductRepository
import pe.edu.upc.bodeguin.ui.view.home.search.ProductAdapter
import pe.edu.upc.bodeguin.ui.view.home.search.ProductListener
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModel
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModelFactory
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class ProductsActivity : AppCompatActivity(), ProductListener {

    private lateinit var toolbar: Toolbar
    private lateinit var imageView: AppCompatImageView
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(applicationContext)
        val api = ApiGateway
        val db = AppDatabase.getInstance(applicationContext)
        val repository = ProductRepository(networkConnectionInterceptor, api, db)
        val factory = ProductViewModelFactory(application, repository)
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)

        val sharedPreferences = getSharedPreferences("data", 0)
        val token = sharedPreferences.getString("token", "")
        val idCategory = sharedPreferences.getInt("idCategory", 0)
        val nameCategory = sharedPreferences.getString("nameCategory", "")
        val urlCategory = sharedPreferences.getString("urlCategory", "")

        productViewModel.setToken(token.toString())
        productViewModel.productListener = this

        // val intent = intent
        // val idCategory = intent.getIntExtra("idCategory", 0)
        // val tvNameCategory = intent.getStringExtra("nameCategory")
        // val urlImage = intent.getStringExtra("urlCategory")

        toolbar = findViewById(R.id.toolbarProducts)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = nameCategory.toString()

        imageView = findViewById(R.id.ivTest)
        Picasso.get().load(urlCategory.toString()).into(imageView)

        val manager = GridLayoutManager(applicationContext, 2)
        rvProductByStore.layoutManager = manager
        productViewModel.getProductsByCategory(idCategory)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onStarted() {
        lottieLoadingProductsCategory.show()
        lottieLoadingProductsCategory.playAnimation()
    }

    override fun onSuccess(products: List<ProductData>) {
        lottieLoadingProductsCategory.hide()
        lottieLoadingProductsCategory.cancelAnimation()
        productAdapter = ProductAdapter(products, applicationContext)
        rvProductByStore.adapter = productAdapter
    }

    override fun onFailure(message: String) {
        lottieLoadingProductsCategory.hide()
        lottieLoadingProductsCategory.cancelAnimation()
        clCategoriesProduct.snackBar(message)
    }
}