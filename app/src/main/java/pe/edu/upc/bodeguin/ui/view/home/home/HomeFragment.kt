package pe.edu.upc.bodeguin.ui.view.home.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.wajahatkarim3.roomexplorer.RoomExplorer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.network.model.response.data.CategoryData
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.repository.CategoryRepository
import pe.edu.upc.bodeguin.ui.viewModel.category.CategoryViewModel
import pe.edu.upc.bodeguin.ui.viewModel.category.CategoryViewModelFactory
import pe.edu.upc.bodeguin.util.hide
import pe.edu.upc.bodeguin.util.show
import pe.edu.upc.bodeguin.util.snackBar

class HomeFragment : Fragment(), HomeListener {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(activity!!.applicationContext)
        val api = ApiGateway
        val db = AppDatabase.getInstance(activity!!.applicationContext)
        val repository = CategoryRepository(networkConnectionInterceptor, api, db)
        val factory = CategoryViewModelFactory(activity!!.application, repository)
        categoryViewModel = ViewModelProvider(this, factory).get(CategoryViewModel::class.java)

        val sharedPreferences = activity!!.getSharedPreferences("data", 0)
        val token = sharedPreferences.getString("token", "")

        categoryViewModel.setToken(token.toString())
        categoryViewModel.homeListener = this

        categoryViewModel.getCategories()
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
        categoryAdapter = CategoryAdapter(categories)
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