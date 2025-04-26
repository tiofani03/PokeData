package id.tiooooo.pokedata.utils.localization

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig
import id.tiooooo.pokedata.data.implementation.local.datastore.AppDatastore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.Locale

//class LocalizationManager(private val context: Context) {
//    private var localizedStrings: Map<String, String> = emptyMap()
//
//    suspend fun loadLanguage(languageCode: String) {
//        withContext(Dispatchers.IO) {
//            try {
//                val fileName = "strings_${languageCode.lowercase(Locale.ROOT)}.json"
//                val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
//                val jsonObject = JSONObject(json)
//
//                val map = mutableMapOf<String, String>()
//                jsonObject.keys().forEach { key ->
//                    map[key] = jsonObject.getString(key)
//                }
//
//                localizedStrings = map
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//
//    fun getString(key: String, vararg args: Any?): String {
//        val rawString = localizedStrings[key] ?: key
//        return try {
//            if (args.isNotEmpty()) {
//                String.format(rawString, *args)
//            } else {
//                rawString
//            }
//        } catch (e: Exception) {
//            rawString
//        }
//    }
//}

class LocalizationManager(
    private val context: Context,
    private val appDatastore: AppDatastore,
) {
    private var localizedStrings: Map<String, String> = emptyMap()

    suspend fun loadLanguage(languageCode: String) {
        withContext(Dispatchers.IO) {
            try {
                val remoteJson = appDatastore.languagePackage.first()
                val map = if (remoteJson.isNotEmpty()) {
                    parseFromRemote(remoteJson, languageCode)
                } else {
                    parseFromAssets(languageCode)
                }

                localizedStrings = map
            } catch (e: Exception) {
                e.printStackTrace()
                localizedStrings = parseFromAssets(languageCode)
            }
        }
    }

    private fun parseFromRemote(json: String, languageCode: String): Map<String, String> {
        val rootObject = JSONObject(json)
        val langObject = rootObject.optJSONObject(languageCode) ?: JSONObject()
        val map = mutableMapOf<String, String>()
        langObject.keys().forEach { key ->
            map[key] = langObject.getString(key)
        }
        return map
    }

    private fun parseFromAssets(languageCode: String): Map<String, String> {
        val fileName = "strings_${languageCode.lowercase(Locale.ROOT)}.json"
        val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(json)
        val map = mutableMapOf<String, String>()
        jsonObject.keys().forEach { key ->
            map[key] = jsonObject.getString(key)
        }
        return map
    }

    fun getString(key: String, vararg args: Any?): String {
        val rawString = localizedStrings[key] ?: key
        return try {
            if (args.isNotEmpty()) {
                String.format(rawString, *args)
            } else {
                rawString
            }
        } catch (e: Exception) {
            rawString
        }
    }
}


class LocalizationProvider(private val manager: LocalizationManager) {
    fun string(key: String, vararg args: Any?): String = manager.getString(key, *args)

}

private val LocalStrings = staticCompositionLocalOf<LocalizationProvider> {
    error("LocalizationProvider not initialized")
}

@Composable
fun ProvideLocalization(
    provider: LocalizationProvider,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalStrings provides provider) {
        content()
    }
}

@Composable
fun stringRes(key: String, vararg args: Any?): String {
    return LocalStrings.current.string(key, *args)
}

@Composable
fun rememberLocalization(appDatastore: AppDatastore): LocalizationProvider {
    val context = LocalContext.current
    val languageCode by appDatastore.selectedLanguage
        .map { it.ifEmpty { "en" } }
        .collectAsState(initial = "en")

    val localizationManager = remember { LocalizationManager(context, appDatastore) }

    return produceState(
        initialValue = LocalizationProvider(localizationManager),
        key1 = languageCode
    ) {
        localizationManager.loadLanguage(languageCode)
        value = LocalizationProvider(localizationManager)
    }.value
}
