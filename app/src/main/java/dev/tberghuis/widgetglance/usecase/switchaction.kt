package dev.tberghuis.widgetglance.usecase

import android.content.Context
import dev.tberghuis.widgetglance.data.ServiceData
import dev.tberghuis.widgetglance.logd
import dev.tberghuis.widgetglance.provideHaHttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

suspend fun postHaSwitchAction(context: Context, entityId: String, action: String) {
  logd("postHaSwitchAction entityId $entityId")

  try {
    context.provideHaHttpClient().post("/api/services/switch/$action") {
      setBody(ServiceData(entityId))
    }
  } catch (e: Exception) {
    logd("error $e")
  }
}