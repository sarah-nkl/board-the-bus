package com.happylication.boardthebus

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.happylication.boardthebus.databinding.ActivityMainBinding
import com.happylication.boardthebus.fragment.FavoritesFragment
import com.happylication.boardthebus.fragment.SearchFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>
    private lateinit var binding: ActivityMainBinding

    private val onNavItemSelectedListener = { item: MenuItem ->
        when (item.itemId) {
            R.id.navigation_favorites -> {
                openFragment(FavoritesFragment())
                true
            }
            R.id.navigation_search -> {
                openFragment(SearchFragment())
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.navigation.setOnNavigationItemSelectedListener(onNavItemSelectedListener)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.content, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
