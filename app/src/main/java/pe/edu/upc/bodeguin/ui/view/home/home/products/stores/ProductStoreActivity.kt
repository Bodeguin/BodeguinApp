package pe.edu.upc.bodeguin.ui.view.home.home.products.stores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_store.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.data.ProductStoreData
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModel
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModelFactory
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class ProductStoreActivity : AppCompatActivity(), StoreListener, KodeinAware {
    private lateinit var toolbar: Toolbar
    private lateinit var imageView: AppCompatImageView
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productStoreAdapter: ProductStoreAdapter
    override val kodein by kodein()
    private val factory: ProductViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_store)
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)

        val sharedPreferences = getSharedPreferences("localData", 0)
        val token = sharedPreferences.getString("token", "")
        val idProduct = sharedPreferences.getInt("idProduct", 0)
        val nameProduct = sharedPreferences.getString("nameProduct", "")
        val urlProduct = sharedPreferences.getString("urlProduct", "")

        productViewModel.setToken(token.toString())
        productViewModel.storeListener = this

        toolbar = findViewById(R.id.toolbarProductsStore)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = nameProduct.toString()

        imageView = findViewById(R.id.ivProduct)
        Picasso.get().load(urlProduct.toString()).into(imageView)

        val manager = GridLayoutManager(applicationContext, 2)
        rvStoreByProduct.layoutManager = manager
        productViewModel.getStoresByProducts(idProduct)
    }

    override fun onStarted() {
        lottieLoadingStoreProducts.show()
        lottieLoadingStoreProducts.playAnimation()
    }

    override fun onSuccess(products: List<ProductStoreData>) {
        lottieLoadingStoreProducts.hide()
        lottieLoadingStoreProducts.cancelAnimation()
        productStoreAdapter = ProductStoreAdapter(products, supportFragmentManager)
        rvStoreByProduct.adapter = productStoreAdapter
    }

    override fun onFailure(message: String) {
        lottieLoadingStoreProducts.hide()
        lottieLoadingStoreProducts.cancelAnimation()
        clProductStore.snackBar(message)
    }
}