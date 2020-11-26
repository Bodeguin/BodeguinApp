package pe.edu.upc.bodeguin.ui.view.home.search

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.data.ProductData
import pe.edu.upc.bodeguin.databinding.ProductPrototypeBinding
import pe.edu.upc.bodeguin.ui.view.home.home.products.ProductsActivity
import pe.edu.upc.bodeguin.ui.view.home.home.products.stores.ProductStoreActivity

class ProductAdapter(
    private val products: List<ProductData>,
    private val context: Context
) : RecyclerView.Adapter<ProductAdapter.PrototypeProduct>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PrototypeProduct(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.product_prototype,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: PrototypeProduct, position: Int) {
        holder.productPrototypeBinding.productModel = products[position]
        holder.itemView.setOnClickListener {
            val id = products[position].id
            val name = products[position].name
            val url = products[position].urlImage
            val intent = Intent(context, ProductStoreActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;

            val editor: SharedPreferences.Editor = context.getSharedPreferences("localData", 0).edit()
            editor.putInt("idProduct", id)
            editor.putString("nameProduct", name)
            editor.putString("urlProduct", url)
            editor.apply()

            context.startActivity(intent)
        }
    }

    inner class PrototypeProduct(
       val productPrototypeBinding: ProductPrototypeBinding
    ): RecyclerView.ViewHolder(productPrototypeBinding.root)
}