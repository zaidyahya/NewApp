package com.example.theapp.ui.myearnings.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.theapp.R
import com.example.theapp.databinding.FragmentTabEarningsBinding
import com.example.theapp.ui.myearnings.adapter.EarningItemAdapter

class EarningsTabFragment : Fragment(R.layout.fragment_tab_earnings) {

    private var _binding: FragmentTabEarningsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTabEarningsBinding.bind(view)

        binding.apply {
            recyclerViewEarningsLog.apply {
                setHasFixedSize(true)
                adapter = EarningItemAdapter()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}