package net.pelozo.poketeams_challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pelozo.poketeams_challenge.api.PokemonService
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    @Provides
    fun provideMoviesService(retrofit: Retrofit): PokemonService =
        retrofit.create(PokemonService::class.java)
}