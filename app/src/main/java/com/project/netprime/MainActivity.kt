package com.project.netprime


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.project.netprime.adapter.FragmentPageAdapter
import com.project.netprime.databinding.ActivityMainBinding
import com.project.netprime.fragments.MovieFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FragmentPageAdapter
    private var TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FragmentPageAdapter(supportFragmentManager, lifecycle)


        with(binding) {
           tabLayout.addTab(
                tabLayout.newTab().setText(resources.getString(R.string.movies))
            )
            tabLayout.addTab(
             tabLayout.newTab().setText(resources.getString(R.string.tv_shows))
            )
            binding.viewPager2.adapter = adapter
//            (binding.viewPager2.get(0) as MovieFragment) .screenView()
            // todo check viewpager size, do type cast and call refresh function

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    Log.d(TAG, "onTabSelected: ")
                    viewPager2.setCurrentItem(tab!!.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    Log.d(TAG, "onTabUnselected: ")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    Log.d(TAG, "onTabReselected: ")
                }

            })
        }

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

}