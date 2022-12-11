package net.pelozo.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val name: String = "",
    var imageUrl: String? = null
): Parcelable
