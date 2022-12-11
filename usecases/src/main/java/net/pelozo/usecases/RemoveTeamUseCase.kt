package net.pelozo.usecases

import net.pelozo.domain.Team
import net.pelozo.domain.repository.PokeTeamsRepository
import javax.inject.Inject

class RemoveTeamUseCase @Inject constructor(
    private val repository: PokeTeamsRepository
) {
    suspend fun invoke(team: Team) =
        repository.removeTeam(team)

}