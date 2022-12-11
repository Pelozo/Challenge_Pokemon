package net.pelozo.poketeams_challenge.modules.selectPokemon.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pelozo.domain.Pokemon
import net.pelozo.poketeams_challenge.base.BaseViewModel
import net.pelozo.usecases.GetPokemonsByRegion
import javax.inject.Inject

@HiltViewModel
class SelectPokemonViewModel @Inject constructor(
    private val getPokemonsByRegion: GetPokemonsByRegion
) : BaseViewModel() {

    private lateinit var region: String
    private val pokemonsMLD = MutableLiveData<List<Pokemon>>()

    fun setUp(_region: String){
        region = _region
        viewModelScope.launch {
             downloadRegionPokemons()
        }
    }

    private suspend fun downloadRegionPokemons() {
        val pokemons =  getPokemonsByRegion.invoke(region.lowercase())
        pokemonsMLD.value = pokemons.sortedBy { it.name }
    }

    fun getPokemons(): LiveData<List<Pokemon>> = pokemonsMLD
}