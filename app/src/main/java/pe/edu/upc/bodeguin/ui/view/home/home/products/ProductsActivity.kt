package pe.edu.upc.bodeguin.ui.view.home.home.products

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_products.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.data.ProductData
import pe.edu.upc.bodeguin.ui.view.home.search.ProductAdapter
import pe.edu.upc.bodeguin.ui.view.home.search.ProductListener
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModel
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModelFactory
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class ProductsActivity : AppCompatActivity(), ProductListener, KodeinAware {

    private lateinit var toolbar: Toolbar
    private lateinit var imageView: AppCompatImageView
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter
    override val kodein by kodein()
    private val factory: ProductViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)

        val sharedPreferences = getSharedPreferences("localData", 0)
        val token = sharedPreferences.getString("token", "")
        val idCategory = sharedPreferences.getInt("idCategory", 0)
        val nameCategory = sharedPreferences.getString("nameCategory", "")
        val urlCategory = sharedPreferences.getString("urlCategory", "")

        productViewModel.setToken(token.toString())
        productViewModel.productListener = this

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