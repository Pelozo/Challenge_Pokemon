package net.pelozo.poketeams_challenge.modules.regionList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pelozo.domain.Region
import net.pelozo.poketeams_challenge.base.BaseViewModel
import net.pelozo.poketeams_challenge.modules.regionList.constants.RegionListConstants.Companion.ALL_TEAMS_ID
import net.pelozo.usecases.GetRegionsUseCase
import javax.inject.Inject

@HiltViewModel
class RegionListVIewModel @Inject constructor(
    private val getRegionsUseCase: GetRegionsUseCase,
) : BaseViewModel() {

    private val regionsMLD = MutableLiveData<List<Region>>()

    init {
        viewModelScope.launch {
            getAvailableRegions()
        }
    }

    private suspend fun getAvailableRegions() {
            val regions = getRegionsUseCase.invoke().toMutableList()
            regions.add(Region(ALL_TEAMS_ID, ALL_TEAMS_ID))
            regions.forEach {
                it.name = it.name.replaceFirstChar(Char::titlecase)
            }
            regionsMLD.value = regions.sortedBy { it.name }
    }

    fun getRegions(): LiveData<List<Region>> = regionsMLD
}