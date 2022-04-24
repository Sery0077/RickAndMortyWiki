package sery.vlasenko.rickandmortywiki.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sery.vlasenko.rickandmortywiki.data.dao.Character
import sery.vlasenko.rickandmortywiki.data.repository.implementations.CharactersListRepository
import javax.inject.Inject

class ViewModelCharacters @Inject constructor(private val repository: CharactersListRepository) :
    ViewModel() {

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
            CoroutineScope(Dispatchers.IO).launch {
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
        getCharacters()
    }
}
