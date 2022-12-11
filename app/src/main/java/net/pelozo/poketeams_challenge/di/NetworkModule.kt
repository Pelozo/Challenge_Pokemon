package net.pelozo.poketeams_challenge.di

import com.firebase.ui.auth.AuthUI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pelozo.poketeams_challenge.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient = OkHttpClient().newBuilder().build()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.POKE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    fun provideAuthUI(): AuthUI {
        return AuthUI.getInstance()
    }
}