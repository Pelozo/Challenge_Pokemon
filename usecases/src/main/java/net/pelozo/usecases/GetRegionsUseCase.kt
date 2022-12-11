package net.pelozo.usecases

import net.pelozo.domain.Region
import net.pelozo.domain.repository.PokeTeamsRepository
import javax.inject.Inject

class GetRegionsUseCase @Inject constructor(
    private val repository: PokeTeamsRepository
) {
    suspend fun invoke(): List<Region> {
        return repository.getRegions()
    }
}