package net.pelozo.domain.responses

data class RegionResponse(
    val id: Int,
    val name: String,
    val url: String,
    val pokedexes: List<PokedexResponse> = emptyList()
)