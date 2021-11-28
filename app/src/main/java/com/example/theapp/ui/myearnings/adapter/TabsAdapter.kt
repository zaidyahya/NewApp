package com.example.theapp.ui.myearnings.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.theapp.ui.myearnings.fragment.EarningsTabFragment
import com.example.theapp.ui.myearnings.fragment.PaymentHistoryTabFragment

class TabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            //0 -> EarningsTabFragment()
            1 -> return PaymentHistoryTabFragment()
        }

        return EarningsTabFragment()
    }
}