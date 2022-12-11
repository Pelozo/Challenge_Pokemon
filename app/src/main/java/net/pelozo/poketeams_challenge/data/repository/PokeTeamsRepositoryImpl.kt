package net.pelozo.poketeams_challenge.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import net.pelozo.domain.Pokemon
import net.pelozo.domain.Region
import net.pelozo.domain.datasource.PokeTeamsDataSource
import net.pelozo.domain.repository.PokeTeamsRepository
import net.pelozo.domain.responses.PlayerInfo
import net.pelozo.poketeams_challenge.api.PokemonService
import net.pelozo.poketeams_challenge.data.mappers.toDomain
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.pelozo.domain.Team

class PokeTeamsRepositoryImpl @Inject constructor(
    private val pokemonService: PokemonService,
    private val pokeTeamsDataSource: PokeTeamsDataSource
) : PokeTeamsRepository {
    override suspend fun getRegions(): List<Region> {
        return pokemonService.getRegions().results.map { it.toDomain() }
    }

    override suspend fun getPlayerTeams(): Flow<Result<PlayerInfo>> {
        return pokeTeamsDataSource.getPlayerInfo()
    }

    override suspend fun getPokemonByRegionId(region: String): List<Pokemon> {
        val pokemons = mutableListOf<Pokemon>()
        val pokedexesResult = pokemonService.getPokemonByRegion(region)

        pokedexesResult.pokedexes.map {
            withContext(Dispatchers.IO) {
                launch {
                    val response = pokemonService.getPokemonByPokedexUrl(it.url)
                    pokemons.addAll(response.pokemons.map { it.toDomain()})
                }
            }
        }
        return pokemons.distinct()
    }

    override suspend fun getPokemonImageByName(name: String): String? =
        pokemonService.getPokemonImageByName(name).sprites.front


    override suspend fun addTeam(team: Team) =
        pokeTeamsDataSource.addTeam(team)

    override suspend fun getTeamsByRegion(region: Region) =
        pokeTeamsDataSource.getTeamsByRegion(region)

    override suspend fun removeTeam(team: Team): Result<String?> =
        pokeTeamsDataSource.removeTeam(team)

    override suspend fun getAllTeams(): Flow<Result<List<Team>>> =
        pokeTeamsDataSource.getAllTeams()

}