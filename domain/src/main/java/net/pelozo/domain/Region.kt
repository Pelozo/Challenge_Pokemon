package net.pelozo.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Region(
    var name: String,
    val url: String
): Parcelable