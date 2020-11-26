package pe.edu.upc.bodeguin.ui.view.home.profile.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_account.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.persistance.model.User
import pe.edu.upc.bodeguin.databinding.FragmentAccountBinding
import pe.edu.upc.bodeguin.ui.view.home.profile.UserListener
import pe.edu.upc.bodeguin.ui.viewModel.profile.UserViewModel
import pe.edu.upc.bodeguin.ui.viewModel.profile.UserViewModelFactory
import pe.edu.upc.bodeguin.util.snackBar

class AccountFragment : DialogFragment(), UserListener, KodeinAware {

    private lateinit var userViewModel: UserViewModel
    override val kodein by kodein()
    private val factory: UserViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreeDialogTheme)

        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        val sharedPreferences = activity!!.getSharedPreferences("localData", 0)
        val id = sharedPreferences.getString("id", "")
        val token = sharedPreferences.getString("token", "")
        userViewModel.cloneUser(id.toString(), token.toString())
        userViewModel.userListener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAccountBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
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
        clAccountDialog.snackBar(message)
    }

    override fun onClose() {
        dismiss()
    }
}