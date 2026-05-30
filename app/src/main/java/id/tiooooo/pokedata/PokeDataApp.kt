package id.tiooooo.pokedata

import android.app.Application
import com.localflow.sdk.Localflow
import id.tiooooo.pokedata.di.LocalflowInitializer
import id.tiooooo.pokedata.di.commonModule
import id.tiooooo.pokedata.di.localModule
import id.tiooooo.pokedata.di.localflowModule
import id.tiooooo.pokedata.di.networkModule
import id.tiooooo.pokedata.di.repositoryModule
import id.tiooooo.pokedata.di.screenModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class PokeDataApp : Application() {
    private val applicationScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Default
    )

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokeDataApp)
            modules(
                listOf(
                    localModule,
                    networkModule,
                    localflowModule,
                    commonModule,
                    repositoryModule,
                    screenModelModule,
                )
            )
        }
        val initializer = GlobalContext.get().get<LocalflowInitializer>()

        applicationScope.launch {
            val config = initializer.createConfig()
            Localflow.initialize(
                context = this@PokeDataApp,
                config = config
            )
        }
    }
}