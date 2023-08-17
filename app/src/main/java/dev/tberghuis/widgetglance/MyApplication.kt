package dev.tberghuis.widgetglance

import android.app.Application
import android.content.Context
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

class MyApplication : Application() {

  val preferencesRepository by lazy { PreferencesRepository(this.dataStore) }

  val haHttpClientFlow by lazy {
    preferencesRepository.haAuthTokenFlow()
      .combine(preferencesRepository.haBaseUrlFlow()) { token, baseUrl ->
        if (token == null || baseUrl == null)
          null
        else
          createHaHttpClient(token, baseUrl)
      }.filterNotNull()
  }
}

// ktx functions

suspend fun Context.provideHaHttpClient(): HttpClient {
  val myApp = applicationContext as MyApplication
  return myApp.haHttpClientFlow.first()
}