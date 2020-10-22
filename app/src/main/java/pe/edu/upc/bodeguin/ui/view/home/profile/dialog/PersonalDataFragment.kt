package pe.edu.upc.bodeguin.ui.view.home.profile.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_personal_data.*
import kotlinx.android.synthetic.main.fragment_personal_data.view.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.persistance.model.User
import pe.edu.upc.bodeguin.data.repository.UserRepository
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personal_data, container, false)
        view.bClose.setOnClickListener {
            dismiss()
        }
        view.bSave.setOnClickListener {
            val email = view.tiEmailDialog.text.toString()
            userViewModel.create(14, email)
        }
        return view
    }

    override fun onSuccess() {
        clPersonalData.snackBar("c logro")
    }

    override fun onSuccessUpdate(user: User) {
        dismiss()
    }

    override fun onFailure(message: String) {
        Log.d("campo", "error: " + message)
        clPersonalData.snackBar(message)
    }
}