package sery.vlasenko.rickandmortywiki.ui.characterinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sery.vlasenko.rickandmortywiki.data.repository.interfaces.ICharacterRepository
import sery.vlasenko.rickandmortywiki.ui.base.BaseViewModel
import javax.inject.Inject

class ViewModelCharacterInfo @Inject constructor(private val repository: ICharacterRepository) :
    BaseViewModel() {

    private val _state: MutableLiveData<CharacterInfoState> =
        MutableLiveData(CharacterInfoState.DataLoading())
    val state: LiveData<CharacterInfoState> = _state

    private var id: Int = 0

    fun onAttach(id: Int) {
        this.id = id
        getCharacter(id)
    }

    private fun getCharacter(id: Int) {
        job = viewModelScope.launch {
            val response = repository.getCharacter(id)
            if (response.data != null) {
                val data = response.data

                _state.postValue(CharacterInfoState.DataLoaded(data))
            } else {
                _state.postValue(CharacterInfoState.DataLoadError(response.error))
            }
        }
    }

    fun onErrorClicked() {
        if (job.isCompleted) {
            getCharacter(id)
        }
    }
}