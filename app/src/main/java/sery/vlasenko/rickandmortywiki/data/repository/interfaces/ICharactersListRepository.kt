package sery.vlasenko.rickandmortywiki.data.repository.interfaces

import sery.vlasenko.rickandmortywiki.data.ResponseData
import sery.vlasenko.rickandmortywiki.data.dao.Characters

interface ICharactersListRepository {
    suspend fun getCharacterList(page: Int?): ResponseData<Characters>
}