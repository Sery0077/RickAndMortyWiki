package sery.vlasenko.rickandmortywiki.ui.characterinfo

import sery.vlasenko.rickandmortywiki.data.dao.Character

sealed class CharacterInfoState {
    class DataLoading : CharacterInfoState()
    class DataLoaded(val data: Character) : CharacterInfoState()
    class DataLoadError(val message: Any?) : CharacterInfoState()
}
