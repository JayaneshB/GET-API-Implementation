package com.project.netprime.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.project.netprime.MainActivity
import com.project.netprime.R
import com.project.netprime.adapter.FragmentPageAdapter
import com.project.netprime.databinding.FragmentShowBinding

class ShowFragment : Fragment() {

    private lateinit var binding : FragmentShowBinding
    private lateinit var adapter: FragmentPageAdapter
    private var Tag = MainActivity::class.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShowBinding.inflate(inflater,container,false)

        adapter = FragmentPageAdapter(childFragmentManager, lifecycle)


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
                    Log.d(Tag, "onTabSelected: ")
                    viewPager2.setCurrentItem(tab!!.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    Log.d(Tag, "onTabUnselected: ")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    Log.d(Tag, "onTabReselected: ")
                }

            })
        }

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })

        return binding.root
    }


}