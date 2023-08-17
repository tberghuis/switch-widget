package dev.tberghuis.widgetglance

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(
  name = "user_preferences",
)

class PreferencesRepository(private val dataStore: DataStore<Preferences>) {

  private fun stringDataFlow(key: String): Flow<String?> {
    return dataStore.data.map { preferences ->
      preferences[stringPreferencesKey(key)]
    }
  }

  private suspend fun updateString(key: String, value: String) {
    dataStore.edit { preferences ->
      preferences[stringPreferencesKey(key)] = value
    }
  }

  fun haBaseUrlFlow(): Flow<String?> {
    return stringDataFlow("ha_base_url")
  }

  suspend fun updateHaBaseUrl(haBaseUrl: String) {
    updateString("ha_base_url", haBaseUrl)
  }

  fun haAuthTokenFlow(): Flow<String?> {
    return stringDataFlow("ha_auth_token")
  }

  suspend fun updateHaAuthToken(haAuthToken: String) {
    updateString("ha_auth_token", haAuthToken)
  }

}