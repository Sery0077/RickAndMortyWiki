package sery.vlasenko.rickandmortywiki.data.repository.implementations

import sery.vlasenko.rickandmortywiki.data.ResponseData
import sery.vlasenko.rickandmortywiki.data.dao.Character
import sery.vlasenko.rickandmortywiki.data.dao.Characters
import sery.vlasenko.rickandmortywiki.data.repository.RickAndMortyService
import sery.vlasenko.rickandmortywiki.data.repository.interfaces.ICharacterRepository
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val service: RickAndMortyService) :
    ICharacterRepository {

    override suspend fun getCharacterList(page: Int?): ResponseData<Characters> {
        return try {
            val response = service.getCharactersList(page)

            if (response.isSuccessful) {
                ResponseData(response.body(), null)
            } else {
                ResponseData(null, response.code())
            }
        } catch (e: Exception) {
            ResponseData(null, e.localizedMessage)
        }
    }

    override suspend fun getCharacter(id: Int): ResponseData<Character> {
        return try {
            val response = service.getCharacter(id)

            if (response.isSuccessful) {
                ResponseData(response.body(), null)
            } else {
                ResponseData(null, response.code())
            }
        } catch (e: Exception) {
            ResponseData(null, e.localizedMessage)
        }
    }
}