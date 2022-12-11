package net.pelozo.poketeams_challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pelozo.domain.datasource.PokeTeamsDataSource
import net.pelozo.poketeams_challenge.data.datasource.remote.PokeTeamsDataSourceImpl

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    fun providesPokeTeamsDataSource(pokeTeamsDataSourceImpl: PokeTeamsDataSourceImpl): PokeTeamsDataSource = pokeTeamsDataSourceImpl

}