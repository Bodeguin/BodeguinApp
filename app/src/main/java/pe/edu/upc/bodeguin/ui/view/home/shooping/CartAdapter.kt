package pe.edu.upc.bodeguin.ui.view.home.shooping

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.persistance.model.Cart
import pe.edu.upc.bodeguin.databinding.CartPrototypeBinding
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModel

class CartAdapter(
    private val context: Context
): RecyclerView.Adapter<CartAdapter.CartPrototype>() {

    private var carts = emptyList<Cart>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CartPrototype(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.cart_prototype,
                parent,
                false
            )
        )

    internal fun setCarts(carts: List<Cart>) {
        this.carts = carts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return carts.size
    }

    override fun onBindViewHolder(holder: CartPrototype, position: Int) {
        holder.cartPrototypeBinding.cardShopping = carts[position]
    }

    private fun getId(position: Int): Int {
        return carts[position].id!!
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder, viewModel: CartViewModel) {
        val id = getId(viewHolder.adapterPosition)
        viewModel.delete(id)
        notifyItemRemoved(viewHolder.adapterPosition + 1)
    }

    inner class CartPrototype(
        val cartPrototypeBinding: CartPrototypeBinding
    ): RecyclerView.ViewHolder(cartPrototypeBinding.root)
}