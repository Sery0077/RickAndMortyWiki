package sery.vlasenko.rickandmortywiki.data.dao

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import sery.vlasenko.rickandmortywiki.ui.base.adapter.RecyclerItem

data class Characters(
    val info: Info,
    @field:Json(name = "results") val characters: List<Character>,
)

data class Info(
    val count: Long,
    val pages: Long,
    val next: String?,
    val prev: String?,
)

@Parcelize
data class Character(
    override val id: String,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
) : RecyclerItem, Parcelable

@Parcelize
data class Location(
    val name: String,
    val url: String,
): Parcelable