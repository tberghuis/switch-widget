package dev.tberghuis.widgetglance.usecase

import android.content.Context
import dev.tberghuis.widgetglance.data.SwitchStateResponse
import dev.tberghuis.widgetglance.logd
import dev.tberghuis.widgetglance.provideHaHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode


suspend fun getHaSwitchState(context: Context, entityId: String): String? {
  logd("getHaSwitchState entityId $entityId")
  val response: HttpResponse
  val state: String
  try {
    response = context.provideHaHttpClient().get("/api/states/$entityId")
    if (response.status != HttpStatusCode.OK) {
      // todo toast message
      logd("get /api/states/$entityId HttpStatusCode ${response.status}")
      // context.sendBroadcastToastReceiver()
      return null
    }
    state = response.body<SwitchStateResponse>().state
  } catch (e: Exception) {
    logd("RefreshClickAction error: $e")
    // todo toast message
    return null
  }
  return state
}