package dev.tberghuis.widgetglance.screens

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Application
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.tberghuis.widgetglance.data.Entity
import dev.tberghuis.widgetglance.glance.RefreshClickAction
import dev.tberghuis.widgetglance.glance.SwitchWidget
import dev.tberghuis.widgetglance.glance.entityIdKey
import dev.tberghuis.widgetglance.logd
import dev.tberghuis.widgetglance.provideHaHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WidgetConfigureScreenViewModel(private val application: Application) :
  AndroidViewModel(application) {
  //  val initialized = mutableStateOf(false)
  val entityIdTextField = MutableStateFlow("")
  val entitySuggestions = mutableStateOf<List<String>>(listOf())
  private var allSwitchEntities: List<String> = listOf()

  val switchLabel = mutableStateOf("")

  val snackbarHostState = SnackbarHostState()


  init {
    viewModelScope.launch {
      allSwitchEntities = fetchEntities()
      resetEntitySuggestions()
//      initialized.value = true

      entityIdTextField.collect { text ->
        if (text == "") {
          resetEntitySuggestions()
        } else if (allSwitchEntities.find { text == it } != null) {
          entitySuggestions.value = listOf()
        } else {
          entitySuggestions.value = allSwitchEntities.filter {
            it.contains(text)
          }
        }
      }
    }
  }

  private fun resetEntitySuggestions() {
    entitySuggestions.value = allSwitchEntities
  }

  fun finish(activity: Activity) {
    viewModelScope.launch {
      if (entityIdTextField.value.isNullOrBlank()) {
        snackbarHostState.showSnackbar("Please enter entity_id")
        return@launch
      }

      val manager = GlanceAppWidgetManager(activity)
      val ids = manager.getGlanceIds(SwitchWidget::class.java)
      // android bug
      // EXTRA_APPWIDGET_ID is 0 when pinned from app activity
      // val glanceId = manager.getGlanceIdBy(appWidgetId)

      val glanceId = ids.maxBy {
        manager.getAppWidgetId(it)
      }

      updateAppWidgetState(activity, glanceId) { prefs ->
        prefs[booleanPreferencesKey("is_configured")] = true
        prefs[stringPreferencesKey("entity_id")] = entityIdTextField.value
        prefs[stringPreferencesKey("label")] = switchLabel.value
      }
      SwitchWidget().update(activity, glanceId)
      val actionParameters = actionParametersOf(entityIdKey to entityIdTextField.value)
      RefreshClickAction().onAction(activity, glanceId, actionParameters)

      SwitchWidget().update(activity, glanceId)

      activity.setResult(RESULT_OK)
      activity.finish()
    }
  }

  // doitwrong
  private suspend fun fetchEntities(): List<String> {

    try {
      val response: HttpResponse = application.provideHaHttpClient().get("/api/states")
      logd("fetchEntities response $response")
      val entities: List<Entity> = response.body()
      logd("entities $entities")
      val switches = entities.filter {
        it.entity_id.startsWith("switch.")
      }
      logd("switches $switches")
      return switches.map { it.entity_id }
    } catch (e: Exception) {
      logd("fetchEntities error: $e")
      snackbarHostState.showSnackbar(e.toString())
    }

    return listOf()
  }
}