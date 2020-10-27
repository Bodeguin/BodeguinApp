package pe.edu.upc.bodeguin.ui.view.home.profile.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBinderMapper
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_personal_data.*
import kotlinx.android.synthetic.main.fragment_personal_data.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.persistance.model.User
import pe.edu.upc.bodeguin.data.repository.UserRepository
import pe.edu.upc.bodeguin.databinding.FragmentPersonalDataBinding
import pe.edu.upc.bodeguin.databinding.FragmentProfileBinding
import pe.edu.upc.bodeguin.ui.view.home.profile.UserListener
import pe.edu.upc.bodeguin.ui.viewModel.profile.UserViewModel
import pe.edu.upc.bodeguin.ui.viewModel.profile.UserViewModelFactory
import pe.edu.upc.bodeguin.util.snackBar

class PersonalDataFragment : DialogFragment(), UserListener {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreeDialogTheme)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(activity!!.applicationContext)
        val api = ApiGateway
        val db = AppDatabase.getInstance(activity!!.applicationContext)
        val repository = UserRepository(networkConnectionInterceptor, api, db)
        val factory = UserViewModelFactory(activity!!.application, repository)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        val sharedPreferences = activity!!.getSharedPreferences("data", 0)
        val id = sharedPreferences.getString("id", "")
        val token = sharedPreferences.getString("token", "")
        userViewModel.cloneUser(id.toString(), token.toString())
        userViewModel.userListener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPersonalDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_data, container, false)
        binding.viewModel = userViewModel
        return binding.root
    }

    override fun onSuccess() {
        TODO("Not yet implemented")
    }

    override fun onSuccessUpdate(user: User) {
        dismiss()
    }

    override fun onFailure(message: String) {
        clPersonalData.snackBar(message)
    }

    override fun onClose() {
        dismiss()
    }
}