package sery.vlasenko.rickandmortywiki.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sery.vlasenko.rickandmortywiki.data.dao.Character
import sery.vlasenko.rickandmortywiki.data.repository.interfaces.ICharacterRepository
import sery.vlasenko.rickandmortywiki.ui.base.BaseViewModel
import javax.inject.Inject

class ViewModelCharacters @Inject constructor(private val repository: ICharacterRepository) :
    BaseViewModel() {

    private val _state: MutableLiveData<CharactersListState> =
        MutableLiveData(CharactersListState.DataLoading())
    val state: LiveData<CharactersListState> = _state

    private val data: ArrayList<Character?> = arrayListOf(null)

    private var hasNextPage = true
    private var maxPage = 1

    init {
        getCharacters()
    }

    private fun getCharacters() {
        if (hasNextPage) {
            job = viewModelScope.launch {
                val response = repository.getCharacterList(maxPage)

                if (response.data != null) {
                    data.apply {
                        removeLast()
                        addAll(response.data.characters)
                        add(null)
                    }

                    _state.postValue(CharactersListState.DataLoaded(data))

                    if (response.data.info.next == null) {
                        hasNextPage = false
                        data.removeLast()
                    } else {
                        maxPage++
                    }
                } else {
                    _state.postValue(CharactersListState.DataLoadError(response.error))
                }
            }
        }
    }

    fun onEndScroll() {
        if (job.isCompleted) {
            getCharacters()
        }
    }

    fun onErrorClicked() {
        if (job.isCompleted) {
            getCharacters()
        }
    }
}