package sery.vlasenko.rickandmortywiki.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import sery.vlasenko.rickandmortywiki.BuildConfig
import sery.vlasenko.rickandmortywiki.data.repository.RickAndMortyService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideRickAndMortyService(): RickAndMortyService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.RICK_AND_MORTY_API)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(RickAndMortyService::class.java)

}
