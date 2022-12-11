package net.pelozo.poketeams_challenge.modules.regionList.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.pelozo.domain.Region
import net.pelozo.poketeams_challenge.base.BaseFragment
import net.pelozo.poketeams_challenge.databinding.FragmentRegionListBinding
import net.pelozo.poketeams_challenge.modules.login.view.LoginFragmentDirections
import net.pelozo.poketeams_challenge.modules.regionList.view.adapters.RegionAdapter
import net.pelozo.poketeams_challenge.modules.regionList.viewModel.RegionListVIewModel
import net.pelozo.poketeams_challenge.observe
import net.pelozo.poketeams_challenge.withViewModel

@AndroidEntryPoint
class RegionListFragment: BaseFragment<FragmentRegionListBinding>() {

    private val regionAdapter = RegionAdapter { region ->
        navigateToTeamList(region)
    }

    override fun initView() {
        super.initView()
        with(binding) {
            rvRegions.adapter = regionAdapter
        }
    }

    override fun getViewModel(): RegionListVIewModel = withViewModel {
        observe(getRegions()){onGetRegions(it)}
    }

    private fun onGetRegions(regions: List<Region>) {
        regionAdapter.submitList(regions)
    }

    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegionListBinding = FragmentRegionListBinding.inflate(inflater, container, false)


    private fun navigateToTeamList(region: Region) {
        findNavController().navigate(
            RegionListFragmentDirections.actionRegionListFragmentToTeamListFragment(region)
        )
    }
}