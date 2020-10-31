package pe.edu.upc.bodeguin.ui.view.home.home.products.stores

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.data.ProductStoreData
import pe.edu.upc.bodeguin.databinding.StoreProductPrototypeBinding
import pe.edu.upc.bodeguin.ui.view.home.home.products.stores.dialog.ShopDialogFragment

class ProductStoreAdapter(
    private val stores: List<ProductStoreData>,
    private val context: FragmentManager
): RecyclerView.Adapter<ProductStoreAdapter.PrototypeProductStore>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PrototypeProductStore(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.store_product_prototype,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return stores.size
    }

    override fun onBindViewHolder(holder: PrototypeProductStore, position: Int) {
        holder.productStorePrototypeBinding.storeModel = stores[position]
        holder.itemView.setOnClickListener {
            val args = Bundle()
            args.putString("productName", stores[position].product)
            args.putString("storeName", stores[position].store)
            args.putString("urlProduct", stores[position].urlImageProduct)
            args.putString("price", stores[position].price)
            args.putString("measureUnit", stores[position].measureUnit)
            args.putDouble("latitude", stores[position].latitude)
            args.putDouble("longitude", stores[position].longitude)
            args.putInt("idInventory", stores[position].id)
            args.putInt("maxQuantity", stores[position].quantity)
            val shopDialog = ShopDialogFragment()
            shopDialog.arguments = args
            shopDialog.show(context, "tag")
        }
    }

    inner class PrototypeProductStore(
        val productStorePrototypeBinding: StoreProductPrototypeBinding
    ): RecyclerView.ViewHolder(productStorePrototypeBinding.root)
}