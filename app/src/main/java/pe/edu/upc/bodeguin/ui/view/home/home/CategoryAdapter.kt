package pe.edu.upc.bodeguin.ui.view.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.data.CategoryData
import pe.edu.upc.bodeguin.databinding.CategoryPrototypeBinding

class CategoryAdapter(
    private val categories: List<CategoryData>
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
    }

    inner class PrototypeCategory(
        val categoryPrototypeBinding: CategoryPrototypeBinding
    ): RecyclerView.ViewHolder(categoryPrototypeBinding.root)
}