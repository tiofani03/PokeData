package id.tiooooo.pokedata.data.implementation.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.tiooooo.pokedata.utils.AppConstants.PREF_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDatastore(
    private val context: Context,
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREF_NAME)

    companion object {
        val USER_UUID = stringPreferencesKey("USER_UUID")
        val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
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

    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data
        .map { prefs ->
            !prefs[USER_UUID].isNullOrEmpty() && prefs[IS_LOGGED_IN] ?: false
        }
}