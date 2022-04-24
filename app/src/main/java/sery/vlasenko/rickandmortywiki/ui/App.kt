package sery.vlasenko.rickandmortywiki.ui

import android.app.Application
import dagger.Component
import sery.vlasenko.rickandmortywiki.di.NetworkModule
import sery.vlasenko.rickandmortywiki.di.ViewModelModule
import sery.vlasenko.rickandmortywiki.ui.characters.FragmentCharacters

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        appComponent = DaggerAppComponent.create()
        super.onCreate()
    }
}

@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    // Fragments
    fun inject(fragment: FragmentCharacters)
}