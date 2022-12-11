package net.pelozo.usecases

import net.pelozo.domain.Pokemon
import net.pelozo.domain.repository.PokeTeamsRepository
import javax.inject.Inject

class GetPokemonsByRegion @Inject constructor(
    private val repository: PokeTeamsRepository
) {
    suspend fun invoke(region: String): List<Pokemon> {
        return repository.getPokemonByRegionId(region)
    }
}