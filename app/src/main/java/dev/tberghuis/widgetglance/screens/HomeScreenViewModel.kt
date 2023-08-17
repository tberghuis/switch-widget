package dev.tberghuis.widgetglance.screens

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import androidx.compose.material3.SnackbarHostState
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.tberghuis.widgetglance.MyApplication
import dev.tberghuis.widgetglance.WidgetConfigureActivity
import dev.tberghuis.widgetglance.glance.SwitchWidgetReceiver
import dev.tberghuis.widgetglance.logd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val application: Application) :
  AndroidViewModel(application) {
  private val prefs = (application as MyApplication).preferencesRepository

  val haBaseUrl: MutableStateFlow<String> = useDatastoreFlow2WayDataFlow(
    viewModelScope,
    prefs.haBaseUrlFlow()
  ) { prefs.updateHaBaseUrl(it) }

  val haAuthToken: MutableStateFlow<String> = useDatastoreFlow2WayDataFlow(
    viewModelScope,
    prefs.haAuthTokenFlow()
  ) { prefs.updateHaAuthToken(it) }

  val snackbarHostState = SnackbarHostState()

  fun requestPinGlanceAppWidget() {
    viewModelScope.launch {
      if (haBaseUrl.value.isNullOrBlank()) {
        snackbarHostState.showSnackbar("Please enter HA Server URL")
        return@launch
      }
      if (haAuthToken.value.isNullOrBlank()) {
        snackbarHostState.showSnackbar("Please enter Access Token")
        return@launch
      }

      val successCallback = PendingIntent.getActivity(
        application,
        0, // todo use a constant
        Intent(application, WidgetConfigureActivity::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
      )

      GlanceAppWidgetManager(application).requestPinGlanceAppWidget(
        SwitchWidgetReceiver::class.java,
        successCallback = successCallback
      )
    }
  }
}


// this is wack, but one way data flow for datastore in the past had bugs
fun useDatastoreFlow2WayDataFlow(
  scope: CoroutineScope,
  prefsFlow: Flow<String?>,
  updatePrefs: suspend (String) -> Unit
): MutableStateFlow<String> {
  val field = MutableStateFlow("")
  scope.launch {
    field.value = prefsFlow.first() ?: ""
    field.drop(1).collect {
      updatePrefs(it)
    }
  }
  return field
}