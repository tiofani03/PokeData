package id.tiooooo.pokedata

import android.app.Application
import id.tiooooo.pokedata.di.databaseModule
import id.tiooooo.pokedata.di.networkModule
import id.tiooooo.pokedata.di.repositoryModule
import id.tiooooo.pokedata.di.screenModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokeDataApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokeDataApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    screenModelModule,
                )
            )
        }
    }
}