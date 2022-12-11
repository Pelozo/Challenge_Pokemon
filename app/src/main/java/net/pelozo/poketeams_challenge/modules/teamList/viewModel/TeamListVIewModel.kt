package net.pelozo.poketeams_challenge.modules.teamList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.pelozo.domain.Region
import net.pelozo.domain.Team
import net.pelozo.poketeams_challenge.base.BaseViewModel
import net.pelozo.poketeams_challenge.modules.regionList.constants.RegionListConstants.Companion.ALL_TEAMS_ID
import net.pelozo.usecases.GetAllTeamsUseCase
import net.pelozo.usecases.GetTeamsByRegionUseCase
import net.pelozo.usecases.RemoveTeamUseCase
import javax.inject.Inject

@HiltViewModel
class TeamListVIewModel @Inject constructor(
    private val getTeamsByRegionUseCase: GetTeamsByRegionUseCase,
    private val getAllTeamsUseCase: GetAllTeamsUseCase,
    private val removeTeamUseCase: RemoveTeamUseCase
) : BaseViewModel() {

    lateinit var region: Region

    private val teamsMLD = MutableLiveData<List<Team>>()

    fun setUp(_region: Region) {
        region = _region
        viewModelScope.launch {
            if (region.name == ALL_TEAMS_ID) {
                downloadAllTeams()
            } else {
                downloadTeamsByRegion()
            }

        }
    }

    private suspend fun downloadAllTeams() {
        getAllTeamsUseCase.invoke().collect {
            val result: List<Team> = it.getOrNull() ?: emptyList()
            teamsMLD.value = result
        }
    }

    private suspend fun downloadTeamsByRegion() {
        getTeamsByRegionUseCase.invoke(region).collect {
            val result: List<Team> = it.getOrNull() ?: emptyList()
            teamsMLD.value = result
        }
    }

    fun removeTeam(team: Team) {
        viewModelScope.launch {
            removeTeamUseCase.invoke(team).getOrNull()
        }
    }

    fun getTeams(): LiveData<List<Team>> = teamsMLD


}