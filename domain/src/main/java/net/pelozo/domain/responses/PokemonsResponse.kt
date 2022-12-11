package net.pelozo.domain.responses

import com.google.gson.annotations.SerializedName

data class PokemonsResponse(
    @SerializedName("pokemon_entries")
    val pokemons: List<PokemonResponse>
)