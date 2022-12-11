package net.pelozo.poketeams_challenge.modules.addTeam.viewModel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pelozo.domain.Pokemon
import net.pelozo.domain.Team
import net.pelozo.poketeams_challenge.Event
import net.pelozo.poketeams_challenge.base.BaseViewModel
import net.pelozo.poketeams_challenge.modules.selectPokemon.constants.SelectPokemonConstants
import net.pelozo.usecases.AddTeamUseCase
import net.pelozo.usecases.GetPokemonImageByName
import net.pelozo.usecases.GetPokemonsByRegion
import javax.inject.Inject

@HiltViewModel
class AddTeamViewModel @Inject constructor(
    private val getPokemonImageByName: GetPokemonImageByName,
    private val addTeamUseCase: AddTeamUseCase
) : BaseViewModel() {

    lateinit var region: String
    private val pokemonAddedMLD = MutableLiveData<List<Pokemon>>()
    private val teamAddedMLD = MutableLiveData<Event<Unit>>()
    private val teamLoadedMLD = MutableLiveData<Team>()

    fun setUp(_region: String, team: Team?) {
        region = _region
        team?.let {
            teamLoadedMLD.value = it
        }
        team?.pokemon?.let {
            pokemonAddedMLD.value = it
        }
    }

    fun fragmentResult(key: String, bundle: Bundle) {
        when (key) {
            SelectPokemonConstants.REQUEST_POKEMON -> {
                val pokemon = bundle.get(SelectPokemonConstants.KEY_POKEMON) as Pokemon
                viewModelScope.launch {
                    pokemon.imageUrl = getPokemonImageByName.invoke(pokemon.name)
                    val addedPokemons = pokemonAddedMLD.value?.toMutableList() ?: mutableListOf()
                    addedPokemons.add(pokemon)
                    pokemonAddedMLD.value = addedPokemons
                }
            }
        }
    }

    fun removePokemon(name: String) {
        val addedPokemons = pokemonAddedMLD.value?.toMutableList() ?: mutableListOf()
        addedPokemons.removeIf { it.name == name }
        teamLoadedMLD.value?.pokemon = addedPokemons
        pokemonAddedMLD.value = addedPokemons
    }

    fun addTeam(name: String, description: String) {
        viewModelScope.launch {
            val addedTeam = addTeamUseCase.invoke(
                Team(
                    id = teamLoadedMLD.value?.id,
                    name = name,
                    number = teamLoadedMLD.value?.number,
                    type = region,
                    description = description,
                    pokemon = pokemonAddedMLD.value ?: emptyList()
                )
            )
            if (addedTeam.isSuccess) {
                teamAddedMLD.value = Event(Unit)
            }
        }
    }

    fun getAddedPokemon(): LiveData<List<Pokemon>> = pokemonAddedMLD
    fun getAddedTeam(): LiveData<Event<Unit>> = teamAddedMLD
    fun getTeamLoaded(): LiveData<Team> = teamLoadedMLD


}