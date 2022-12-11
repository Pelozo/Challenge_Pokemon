package net.pelozo.domain.datasource

import kotlinx.coroutines.flow.Flow
import net.pelozo.domain.Region
import net.pelozo.domain.Team
import net.pelozo.domain.responses.PlayerInfo

interface PokeTeamsDataSource {
    suspend fun getPlayerInfo(): Flow<Result<PlayerInfo>>
    suspend fun addTeam(team: Team): Result<String?>
    suspend fun getTeamsByRegion(region: Region): Flow<Result<List<Team>>>
    suspend fun removeTeam(team: Team): Result<String?>
    suspend fun getAllTeams(): Flow<Result<List<Team>>>
}