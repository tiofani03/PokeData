package id.tiooooo.pokedata.di

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.localflow.sdk.Localflow
import com.localflow.sdk.LocalflowConfig
import id.tiooooo.pokedata.data.implementation.local.datastore.AppDatastore
import id.tiooooo.pokedata.utils.AppConstants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.dsl.module

val localflowModule = module {
    single<LocalflowInitializer> {
        LocalflowInitializer(
            appDatastore = get()
        )
    }
}

class LocalflowInitializer(
    private val appDatastore: AppDatastore
) {
    suspend fun createConfig(): LocalflowConfig {
        val languageCode = appDatastore.selectedLanguage
            .first()
            .ifEmpty { "en" }

        return LocalflowConfig.Builder(
            apiKey = AppConstants.LOCAL_FLOW_API_KEY,
            baseUrl = AppConstants.LOCAL_FLOW_BASE_URL
        )
            .defaultLanguage(languageCode)
            .syncIntervalMs(30 * 60 * 1000L)
            .enableAutoSync(true)
            .logLevel(LocalflowConfig.LogLevel.VERBOSE)
            .fallbackAssetPath("localizations.json")
            .build()
    }
}