package com.example.theapp.ui.market.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentMarketBinding
import com.example.theapp.model.Catalogue
import com.example.theapp.model.CatalogueNew
import com.example.theapp.ui.market.adapter.BackupItemAdapter
import com.example.theapp.ui.market.adapter.BackupItemAdapterNew
import com.example.theapp.ui.market.adapter.MarketItemAdapter
import com.example.theapp.ui.market.viewmodel.MarketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : Fragment(R.layout.fragment_market), BackupItemAdapter.OnItemCLickListener, MarketItemAdapter.OnItemCLickListener, BackupItemAdapterNew.OnItemClickListener {

    private val viewModel: MarketViewModel by viewModels()

    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!

    private val backupAdapter = BackupItemAdapter(this)
    private val backupAdapterNew = BackupItemAdapterNew(this)
    private val marketAdapter = MarketItemAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMarketBinding.bind(view)

        binding.apply {
            recyclerViewMarket.apply {
                setHasFixedSize(true)
                //adapter = backupAdapter
                //adapter = marketAdapter
                adapter = backupAdapterNew
            }
        }

        //viewModel.catalogues.observe(viewLifecycleOwner){ // With viewModels, this seems to be the correct way to do it instead of passing the list as parameter to the adapter
        //    backupAdapter.setCatalogues(it) // Because 1) the way data is retrieved from the ViewModel + Repository; don't have to go down the init route (i.e. where & when?) + it might be how things are done with API calls
        //}                                   // Here the lifeCycleChanges observing handles passing it to the adapter the first time as well. Thus, we don't have to explicitly pass it as parameter.
                                            // Even though at this point, we might not necessarily need to 'observe'. Because this data isn't modified by the user. But that wouldn't be all, data can get refreshed from API thus does need observation, and so this would be the way to go

        viewModel.pagingCatalogues.observe(viewLifecycleOwner) {
            Log.d("PFragment", "Observed?")
            //marketAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.newCatalogues.observe(viewLifecycleOwner) {
            Log.d("NewCats", "Observed!")
            backupAdapterNew.submitList(it)
        }

        //viewModel.insert()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(catalogue: Catalogue) {
        Log.d("OnItemClick", "$catalogue")
        //viewModel.insert()
        //val action = MarketFragmentDirections.actionMarketFragmentToCatalogueFragment(catalogue)
        //findNavController().navigate(action)

    }

    override fun onItemClick(catalogue: CatalogueNew) {
        Log.d("OnItemClickNew", "$catalogue")
        val action = MarketFragmentDirections.actionMarketFragmentToCatalogueFragment(catalogue)
        findNavController().navigate(action)
    }

}