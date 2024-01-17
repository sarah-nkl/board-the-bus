package com.appcessible.boardthebus

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.appcessible.boardthebus.databinding.ActivityMainBinding
import com.appcessible.boardthebus.fragment.FavoritesFragment
import com.appcessible.boardthebus.fragment.SearchFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val pageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.navigation.selectedItemId = when (position) {
                0 -> R.id.favorites_fragment
                1 -> R.id.search_fragment
                else -> throw IllegalStateException("ViewPager has less than $position items")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewPager.adapter = ScreenSlidePagerAdapter(this)
        binding.navigation.setOnItemSelectedListener { item ->
            binding.viewPager.currentItem = if (item.itemId == R.id.favorites_fragment) {
                0
            } else if (item.itemId == R.id.search_fragment) {
                1
            } else {
                throw IllegalStateException("Item with itemId ${item.itemId} not found")
            }
            true
        }

        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
    }
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment = when(position) {
            0 -> FavoritesFragment()
            1 -> SearchFragment()
            else -> throw IllegalStateException("ViewPager should only have $itemCount fragments")
        }
    }
}
