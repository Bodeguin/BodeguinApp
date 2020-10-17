package pe.edu.upc.bodeguin.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.ui.view.fragments.HomeFragment
import pe.edu.upc.bodeguin.ui.view.fragments.ProfileFragment
import pe.edu.upc.bodeguin.ui.view.fragments.ShoppingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateTo(bnHome.menu.findItem(R.id.iHome))
        bnHome.selectedItemId = R.id.iHome

        bnHome.setOnNavigationItemSelectedListener { item ->
            navigateTo(item)
            true
        }
    }

    private fun navigateTo(item: MenuItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flContent, fragmentFor(item))
            .commit()
    }

    private fun fragmentFor(item: MenuItem): Fragment {
        return when (item.itemId) {
            R.id.iShopping -> ShoppingFragment()
            R.id.iHome -> HomeFragment()
            R.id.iProfile -> ProfileFragment()
            else -> HomeFragment()
        }
    }
}