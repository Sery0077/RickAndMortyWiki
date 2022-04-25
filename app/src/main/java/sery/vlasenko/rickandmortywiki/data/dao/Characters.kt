package sery.vlasenko.rickandmortywiki.data.dao

import com.squareup.moshi.Json
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
) : RecyclerItem

data class Location(
    val name: String,
    val url: String,
)