package id.tiooooo.pokedata.data.implementation.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.tiooooo.pokedata.utils.AppConstants.PREF_NAME
import id.tiooooo.pokedata.utils.AppLanguage
import id.tiooooo.pokedata.utils.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDatastore(
    private val context: Context,
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREF_NAME)

    companion object {
        val USER_UUID = stringPreferencesKey("USER_UUID")
        val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
        val IS_ALREADY_LOADED = booleanPreferencesKey("IS_ALREADY_LOADED")
        val ACTIVE_THEME = stringPreferencesKey("ACTIVE_THEME")
        val SELECTED_LANGUAGE = stringPreferencesKey("SELECTED_LANGUAGE")
    }

    suspend fun setLoginStatus(
        uuid: String,
        isLoggedIn: Boolean
    ) {
        context.dataStore.edit { prefs ->
            prefs[USER_UUID] = uuid
            prefs[IS_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun setLogout() {
        context.dataStore.edit { prefs ->
            prefs[USER_UUID] = ""
            prefs[IS_LOGGED_IN] = false
        }
    }

    suspend fun setAlreadyLoaded(state: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[IS_ALREADY_LOADED] = state
        }
    }

    suspend fun setActiveTheme(theme: String) {
        context.dataStore.edit { prefs ->
            prefs[ACTIVE_THEME] = theme
        }
    }

    suspend fun setSelectedLanguage(value: String) {
        context.dataStore.edit { prefs ->
            prefs[SELECTED_LANGUAGE] = value
        }
    }

    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data
        .map { prefs ->
            !prefs[USER_UUID].isNullOrEmpty() && prefs[IS_LOGGED_IN] ?: false
        }

    val userUuid: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[USER_UUID].orEmpty()
    }

    val isAlreadyLoaded: Flow<Boolean> = context.dataStore.data
        .map { prefs ->
            prefs[IS_ALREADY_LOADED] ?: false
        }

    val activeTheme: Flow<String> = context.dataStore.data
        .map { prefs ->
            prefs[ACTIVE_THEME] ?: AppTheme.SYSTEM.label
        }

    val selectedLanguage: Flow<String> = context.dataStore.data
        .map { prefs ->
            prefs[SELECTED_LANGUAGE] ?: AppLanguage.ENGLISH.code
        }
}