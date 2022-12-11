package net.pelozo.poketeams_challenge.api

import net.pelozo.domain.responses.PokedexResponse
import net.pelozo.domain.responses.PokemonImageResponse
import net.pelozo.domain.responses.PokemonsResponse
import net.pelozo.domain.responses.RegionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokemonService {

    @GET("region/")
    suspend fun getRegions(): RegionsResponse

    @GET("region/{id}")
    suspend fun getPokemonByRegion(@Path("id") region: String): PokedexResponse

    @GET
    suspend fun getPokemonByPokedexUrl(@Url url: String): PokemonsResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonImageByName(@Path("name") name: String): PokemonImageResponse


}