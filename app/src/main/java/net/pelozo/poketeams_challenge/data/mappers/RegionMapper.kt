package net.pelozo.poketeams_challenge.data.mappers

import net.pelozo.domain.Pokedex
import net.pelozo.domain.Region
import net.pelozo.domain.responses.PokedexResponse
import net.pelozo.domain.responses.RegionResponse

fun RegionResponse.toDomain(): Region =
    Region(
        name = this.name,
        url = this.url
    )

/*
fun PokedexResponse.toDomain(): Pokedex =
    Pokedex(
        name = this.name,
        url = this.url
    )

 */