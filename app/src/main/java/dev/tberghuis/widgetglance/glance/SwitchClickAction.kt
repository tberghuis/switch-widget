package dev.tberghuis.widgetglance.glance

import android.content.Context
import android.widget.Toast
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.ToggleableStateKey
import androidx.glance.appwidget.state.updateAppWidgetState
import dev.tberghuis.widgetglance.data.ServiceData
import dev.tberghuis.widgetglance.data.SwitchStateResponse
import dev.tberghuis.widgetglance.logd
import dev.tberghuis.widgetglance.provideHaHttpClient
import dev.tberghuis.widgetglance.sendBroadcastToastReceiver
import dev.tberghuis.widgetglance.usecase.postHaSwitchAction
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SwitchClickAction : ActionCallback {
  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters
  ) {
    val checked = requireNotNull(parameters[ToggleableStateKey]) {
      "This action should only be called in response to toggleable events"
    }
    val entityId = requireNotNull(parameters[entityIdKey]) {
      "Add $entityIdKey parameter in the ActionParameters."
    }
    logd("SwitchClickAction checked $checked glanceId $glanceId")

    updateAppWidgetState(context, glanceId) { state ->
      state[booleanPreferencesKey("is_on")] = checked
    }
    SwitchWidget().update(context, glanceId)

    // todo errors to toast, broadcast receiver
//    try {
//      // call HA rest api
//      val action = if (checked) "turn_on" else "turn_off"
//      context.provideHaHttpClient().post("/api/services/switch/$action") {
//        setBody(ServiceData(entityId))
//      }
//    } catch (e: Exception) {
//      logd("error $e")
//    }
    val action = if (checked) "turn_on" else "turn_off"
    postHaSwitchAction(context, entityId, action)
  }
}

val entityIdKey = ActionParameters.Key<String>("EntityIdKey")

class RefreshClickAction : ActionCallback {
  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters
  ) {
    val entityId = requireNotNull(parameters[entityIdKey]) {
      "Add $entityIdKey parameter in the ActionParameters."
    }
    val response: HttpResponse
    val state: String
    try {
      response = context.provideHaHttpClient().get("/api/states/$entityId")
      if (response.status != HttpStatusCode.OK) {
        // todo toast message
        logd("get /api/states/$entityId HttpStatusCode ${response.status}")
        // context.sendBroadcastToastReceiver()
        return
      }
      state = response.body<SwitchStateResponse>().state
    } catch (e: Exception) {
      logd("RefreshClickAction error: $e")
      // todo toast message
      return
    }

    logd("RefreshClickAction response $response")
    logd("RefreshClickAction state $state")

    val isOn = state == "on"

    updateAppWidgetState(context, glanceId) { prefs ->
      prefs[booleanPreferencesKey("is_on")] = isOn
    }
    SwitchWidget().update(context, glanceId)
  }
}

//class SwitchBoxClickAction : ActionCallback {
//  override suspend fun onAction(
//    context: Context,
//    glanceId: GlanceId,
//    parameters: ActionParameters
//  ) {
//    updateAppWidgetState(context, glanceId) { state ->
//      val isOn = state[booleanPreferencesKey("is_on")]
//      logd("SwitchBoxClickAction isOn $isOn")
//    }
//    SwitchWidget().update(context, glanceId)
//  }
//}



