package com.hasib.fragmentinfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.hasib.fragmentinfragment.databinding.FragmentParent1Binding

class Parent1Fragment : Fragment() {

    private lateinit var binding: FragmentParent1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParent1Binding.inflate(layoutInflater, container, false)

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        adapter.addFragment(Child1Fragment())
        adapter.addFragment(Child2Fragment())

        binding.viewPagerParent.adapter = adapter

        binding.tabLayoutParent.addTab(binding.tabLayoutParent.newTab().setText("First C"))
        binding.tabLayoutParent.addTab(binding.tabLayoutParent.newTab().setText("Second C"))

        binding.tabLayoutParent.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.viewPagerParent.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.viewPagerParent.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayoutParent.selectTab(binding.tabLayoutParent.getTabAt(position))
            }
        })

        return binding.root
    }
}