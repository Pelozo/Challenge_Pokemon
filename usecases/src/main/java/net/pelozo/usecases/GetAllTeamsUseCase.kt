package net.pelozo.usecases


import net.pelozo.domain.repository.PokeTeamsRepository
import javax.inject.Inject

class GetAllTeamsUseCase @Inject constructor(
    private val repository: PokeTeamsRepository
) {
    suspend fun invoke() =
        repository.getAllTeams()

}