package com.example.theapp.ui.myearnings.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.theapp.R
import com.example.theapp.databinding.FragmentMyEarningsBinding
import com.example.theapp.ui.myearnings.adapter.TabsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MyEarningsFragment : Fragment(R.layout.fragment_my_earnings) {

    private var _binding: FragmentMyEarningsBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabsAdapter: TabsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMyEarningsBinding.bind(view)

        tabsAdapter = TabsAdapter(this)
        binding.viewPagerMyEarningsTabs.adapter = tabsAdapter

        TabLayoutMediator(
            binding.tabLayoutMyEarnings,
            binding.viewPagerMyEarningsTabs,
        ) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "Earnings"
                    //tab.icon = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_baseline_attach_money_24) }
                }
                1 -> {
                    tab.text = "Payment History"
                    //tab.icon = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_baseline_attach_money_24) }
                }
            }
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}