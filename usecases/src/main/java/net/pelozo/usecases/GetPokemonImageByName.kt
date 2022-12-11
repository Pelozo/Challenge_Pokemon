package net.pelozo.usecases

import net.pelozo.domain.repository.PokeTeamsRepository
import javax.inject.Inject

class GetPokemonImageByName @Inject constructor(
    private val repository: PokeTeamsRepository
) {
    suspend fun invoke(name: String): String? {
        return repository.getPokemonImageByName(name)
    }
}