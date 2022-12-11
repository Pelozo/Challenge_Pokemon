package net.pelozo.poketeams_challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pelozo.domain.repository.PokeTeamsRepository
import net.pelozo.poketeams_challenge.data.repository.PokeTeamsRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun providesPokeTeamsRepository(pokeTeamsRepositoryImpl: PokeTeamsRepositoryImpl): PokeTeamsRepository = pokeTeamsRepositoryImpl

}