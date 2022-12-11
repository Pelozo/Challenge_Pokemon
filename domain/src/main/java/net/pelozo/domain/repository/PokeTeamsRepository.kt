package net.pelozo.domain.repository

import kotlinx.coroutines.flow.Flow
import net.pelozo.domain.Pokemon
import net.pelozo.domain.Region
import net.pelozo.domain.Team
import net.pelozo.domain.responses.PlayerInfo

interface PokeTeamsRepository {
    suspend fun getRegions(): List<Region>
    suspend fun getPlayerTeams(): Flow<Result<PlayerInfo>>
    suspend fun getPokemonByRegionId(region: String): List<Pokemon>
    suspend fun getPokemonImageByName(name: String): String?
    suspend fun addTeam(team: Team): Result<String?>
    suspend fun getTeamsByRegion(region: Region): Flow<Result<List<Team>>>
    suspend fun removeTeam(team: Team): Result<String?>
    suspend fun getAllTeams(): Flow<Result<List<Team>>>

}