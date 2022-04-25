package sery.vlasenko.rickandmortywiki.ui.characters

import sery.vlasenko.rickandmortywiki.data.dao.Character

sealed class CharactersListState {
    class DataLoading : CharactersListState()
    class DataLoaded(val data: List<Character?>) : CharactersListState()
    class DataLoadError(val message: Any?) : CharactersListState()
}