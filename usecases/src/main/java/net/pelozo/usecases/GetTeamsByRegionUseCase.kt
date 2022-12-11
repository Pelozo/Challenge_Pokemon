package net.pelozo.usecases

import net.pelozo.domain.Region
import net.pelozo.domain.repository.PokeTeamsRepository
import javax.inject.Inject

class GetTeamsByRegionUseCase @Inject constructor(
    private val repository: PokeTeamsRepository
) {
    suspend fun invoke(region: Region) =
        repository.getTeamsByRegion(region)

}