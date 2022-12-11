package net.pelozo.poketeams_challenge.data.mappers

import net.pelozo.domain.Pokemon
import net.pelozo.domain.responses.PokemonResponse

fun PokemonResponse.toDomain(): Pokemon =
    Pokemon(
        name = this.pokemonSpecies.name,
        imageUrl = ""
    )