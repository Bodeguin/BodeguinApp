package pe.edu.upc.bodeguin.ui.view.home.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.databinding.FragmentProfileBinding
import pe.edu.upc.bodeguin.ui.view.home.profile.dialog.AccountFragment
import pe.edu.upc.bodeguin.ui.view.home.profile.dialog.DirectionFragment
import pe.edu.upc.bodeguin.ui.view.home.profile.dialog.PersonalDataFragment
import pe.edu.upc.bodeguin.ui.viewModel.profile.UserViewModel
import pe.edu.upc.bodeguin.ui.viewModel.profile.UserViewModelFactory
import org.kodein.di.android.x.kodein

class ProfileFragment : Fragment(), KodeinAware {

    private lateinit var userViewModel: UserViewModel
    override val kodein by kodein()
    private val factory: UserViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = userViewModel
        binding.lifecycleOwner = this
        binding.root.ibPersonalData.setOnClickListener {
            val personalDialog = PersonalDataFragment()
            personalDialog.show(activity!!.supportFragmentManager, "tag")
        }
        binding.root.ibDirection.setOnClickListener {
            val directionDialog = DirectionFragment()
            directionDialog.show(activity!!.supportFragmentManager, "tag2")
        }
        binding.root.ibUserAccount.setOnClickListener {
            val accountDialog = AccountFragment()
            accountDialog.show(activity!!.supportFragmentManager, "tag3")
        }
        return binding.root
    }
}
