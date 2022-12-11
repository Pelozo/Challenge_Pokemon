package net.pelozo.poketeams_challenge.modules.teamList.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import net.pelozo.domain.Team
import net.pelozo.poketeams_challenge.base.BaseFragment
import net.pelozo.poketeams_challenge.databinding.FragmentTeamListBinding
import net.pelozo.poketeams_challenge.modules.regionList.constants.RegionListConstants.Companion.ALL_TEAMS_ID
import net.pelozo.poketeams_challenge.modules.teamList.view.adapter.TeamAdapter
import net.pelozo.poketeams_challenge.modules.teamList.viewModel.TeamListVIewModel
import net.pelozo.poketeams_challenge.observe
import net.pelozo.poketeams_challenge.withViewModel

@AndroidEntryPoint
class TeamListFragment : BaseFragment<FragmentTeamListBinding>() {

    val args: TeamListFragmentArgs by navArgs()

    private val teamAdapter = TeamAdapter(
        {
            navigateToEditTeam(it)
        }, {
            getViewModel().removeTeam(it)
        }
    )

    override fun initView() {
        super.initView()
        with(binding) {
            rvTeams.adapter = teamAdapter
            btAdd.setOnClickListener {
                navigateToAddTeam()
            }
            btAdd.isVisible = args.region.name != ALL_TEAMS_ID
        }
    }

    override fun setUp(arguments: Bundle?) {
        super.setUp(arguments)
        getViewModel().setUp(args.region)
    }


    override fun getViewModel(): TeamListVIewModel = withViewModel {
        observe(getTeams()) { onGetTeams(it) }
    }

    private fun onGetTeams(teams: List<Team>) {
        teamAdapter.submitList(teams)
        binding.tvEmptyTeams.isVisible = teams.isEmpty()
    }

    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentTeamListBinding = FragmentTeamListBinding.inflate(inflater, container, false)

    private fun navigateToAddTeam() {
        findNavController().navigate(
            TeamListFragmentDirections.actionTeamListFragmentToAddTeamFragment(
                args.region.name
            )
        )
    }
    private fun navigateToEditTeam(team: Team) {
        team.type?.let { region ->
            findNavController().navigate(
                TeamListFragmentDirections.actionTeamListFragmentToAddTeamFragment(
                    region,
                    team
                )
            )
        }

    }
}