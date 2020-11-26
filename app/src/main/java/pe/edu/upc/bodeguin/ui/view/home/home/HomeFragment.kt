package pe.edu.upc.bodeguin.ui.view.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.data.CategoryData
import pe.edu.upc.bodeguin.ui.viewModel.category.CategoryViewModel
import pe.edu.upc.bodeguin.ui.viewModel.category.CategoryViewModelFactory
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class HomeFragment : Fragment(), HomeListener, KodeinAware {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var token: String

    override val kodein by kodein()
    private val factory: CategoryViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel = ViewModelProvider(this, factory).get(CategoryViewModel::class.java)

        val sharedPreferences = activity!!.getSharedPreferences("localData", 0)
        val userToken = sharedPreferences.getString("token", "")
        token = "Bearer $userToken"

        categoryViewModel.setToken(userToken.toString())
        categoryViewModel.homeListener = this

        categoryViewModel.getCategories(token)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = GridLayoutManager(activity, 2)
        rvCategories.layoutManager = manager
    }

    override fun onSuccess(categories: List<CategoryData>) {
        activity!!.lottieLoadingMain.hide()
        activity!!.lottieLoadingMain.cancelAnimation()
        categoryAdapter = CategoryAdapter(categories, context!!)
        rvCategories.adapter = categoryAdapter
        rvCategories.adapter!!.notifyDataSetChanged()
    }

    override fun onFailure(message: String) {
        activity!!.lottieLoadingMain.hide()
        activity!!.lottieLoadingMain.cancelAnimation()
        clHome.snackBar(message)
    }

    override fun onStarted() {
        activity!!.lottieLoadingMain.show()
        activity!!.lottieLoadingMain.playAnimation()
    }
}