package sery.vlasenko.rickandmortywiki.data.repository.interfaces

import sery.vlasenko.rickandmortywiki.data.ResponseData
import sery.vlasenko.rickandmortywiki.data.dao.Character
import sery.vlasenko.rickandmortywiki.data.dao.Characters

interface ICharacterRepository {
    suspend fun getCharacterList(page: Int?): ResponseData<Characters>
    suspend fun getCharacter(id: Int): ResponseData<Character>
}