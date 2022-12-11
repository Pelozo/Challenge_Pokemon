package net.pelozo.domain.responses

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("entry_number")
    val pokemonEntry: Int,
    @SerializedName("pokemon_species")
    val pokemonSpecies: PokemonSpecieResponse
)
