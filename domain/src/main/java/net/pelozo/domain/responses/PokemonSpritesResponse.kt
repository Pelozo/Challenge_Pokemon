package net.pelozo.domain.responses

import com.google.gson.annotations.SerializedName

data class PokemonSpritesResponse(
    @SerializedName("front_default")
    val front: String?
)