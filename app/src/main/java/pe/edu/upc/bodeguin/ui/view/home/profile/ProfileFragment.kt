package pe.edu.upc.bodeguin.ui.view.home.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.databinding.FragmentProfileBinding
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.repository.UserRepository
import pe.edu.upc.bodeguin.ui.viewModel.profile.UserViewModel
import pe.edu.upc.bodeguin.ui.viewModel.profile.UserViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(activity!!.applicationContext)
        val api = ApiGateway
        val db = AppDatabase.getInstance(activity!!.applicationContext)
        val repository = UserRepository(networkConnectionInterceptor, api, db)
        val factory = UserViewModelFactory(activity!!.application, repository)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = userViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}