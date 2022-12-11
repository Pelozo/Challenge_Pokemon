package net.pelozo.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    var id: String? = null,
    var name: String? = null,
    var number: Int? = null,
    var type: String? = null,
    var description: String? = null,
    var pokemon: List<Pokemon>? = emptyList()
): Parcelable