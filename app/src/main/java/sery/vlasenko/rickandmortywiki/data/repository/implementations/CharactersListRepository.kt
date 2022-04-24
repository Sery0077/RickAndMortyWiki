package sery.vlasenko.rickandmortywiki.data.repository.implementations

import sery.vlasenko.rickandmortywiki.data.ResponseData
import sery.vlasenko.rickandmortywiki.data.dao.Characters
import sery.vlasenko.rickandmortywiki.data.repository.RickAndMortyService
import sery.vlasenko.rickandmortywiki.data.repository.interfaces.ICharactersListRepository
import java.io.IOException
import javax.inject.Inject

class CharactersListRepository @Inject constructor(private val service: RickAndMortyService) :
    ICharactersListRepository {

    override suspend fun getCharacterList(page: Int?): ResponseData<Characters> {
        return try {
            val response = service.getCharactersList(page)

            if (response.isSuccessful) {
                ResponseData(response.body(), null)
            } else {
                ResponseData(null, response.code())
            }
        } catch (e: IOException) {
            ResponseData(null, e.localizedMessage)
        }
    }
}