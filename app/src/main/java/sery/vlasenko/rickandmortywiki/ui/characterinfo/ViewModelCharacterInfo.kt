package sery.vlasenko.rickandmortywiki.ui.characterinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sery.vlasenko.rickandmortywiki.data.repository.implementations.CharacterRepository
import javax.inject.Inject

class ViewModelCharacterInfo @Inject constructor(private val repository: CharacterRepository) :
    ViewModel() {

    private val _state: MutableLiveData<CharacterInfoState> =
        MutableLiveData(CharacterInfoState.DataLoading())
    val state: LiveData<CharacterInfoState> = _state

    fun onAttach(id: Int) {
        getCharacter(id)
    }

    private fun getCharacter(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getCharacter(id)

            if (response.data != null) {
                val data = response.data

                _state.postValue(CharacterInfoState.DataLoaded(data))
            } else {
                _state.postValue(CharacterInfoState.DataLoadError(response.error))
            }
        }
    }
}