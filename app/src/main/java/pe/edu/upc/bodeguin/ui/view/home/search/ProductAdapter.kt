package pe.edu.upc.bodeguin.ui.view.home.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.data.ProductData
import pe.edu.upc.bodeguin.databinding.ProductPrototypeBinding

class ProductAdapter(
    private val products: List<ProductData>
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
    }

    inner class PrototypeProduct(
       val productPrototypeBinding: ProductPrototypeBinding
    ): RecyclerView.ViewHolder(productPrototypeBinding.root)
}