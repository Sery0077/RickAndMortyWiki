package sery.vlasenko.rickandmortywiki.data.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sery.vlasenko.rickandmortywiki.data.dao.Characters

interface RickAndMortyService {
    @GET("character")
    suspend fun getCharactersList(@Query("page") page: Int?): Response<Characters>
}