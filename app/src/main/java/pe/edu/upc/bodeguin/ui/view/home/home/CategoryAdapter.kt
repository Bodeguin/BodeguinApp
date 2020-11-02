package pe.edu.upc.bodeguin.ui.view.home.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.data.CategoryData
import pe.edu.upc.bodeguin.databinding.CategoryPrototypeBinding
import pe.edu.upc.bodeguin.ui.view.home.home.products.ProductsActivity

class CategoryAdapter(
    private val categories: List<CategoryData>,
    private val context: Context
): RecyclerView.Adapter<CategoryAdapter.PrototypeCategory>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PrototypeCategory(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.category_prototype,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: PrototypeCategory, position: Int) {
        holder.categoryPrototypeBinding.categoryModel = categories[position]
        holder.itemView.setOnClickListener {
            val id = categories[position].id
            val name = categories[position].name
            val url = categories[position].urlImage
            val intent = Intent(context, ProductsActivity::class.java)

            val editor: SharedPreferences.Editor = context.getSharedPreferences("data", 0).edit()
            editor.putInt("idCategory", id)
            editor.putString("nameCategory", name)
            editor.putString("urlCategory", url)
            editor.apply()

            context.startActivity(intent)
        }
    }

    inner class PrototypeCategory(
        val categoryPrototypeBinding: CategoryPrototypeBinding
    ): RecyclerView.ViewHolder(categoryPrototypeBinding.root)
}