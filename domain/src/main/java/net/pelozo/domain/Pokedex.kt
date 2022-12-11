package net.pelozo.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokedex(
    val name: String,
    val url: String
): Parcelable