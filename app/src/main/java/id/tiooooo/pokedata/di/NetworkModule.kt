package id.tiooooo.pokedata.di

import id.tiooooo.pokedata.data.implementation.remote.service.PokeService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<PokeService> {
        get<Retrofit>().create(PokeService::class.java)
    }
}
