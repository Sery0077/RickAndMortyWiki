package sery.vlasenko.rickandmortywiki.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import sery.vlasenko.rickandmortywiki.ui.characterinfo.ViewModelCharacterInfo
import sery.vlasenko.rickandmortywiki.ui.characters.ViewModelCharacters
import sery.vlasenko.rickandmortywiki.ui.vmfactories.ViewModelFactory

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelCharacters::class)
    abstract fun bindViewModelCharacters(viewModel: ViewModelCharacters): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelCharacterInfo::class)
    abstract fun bindMViewModelCharacterInfo(viewModel: ViewModelCharacterInfo): ViewModel
}