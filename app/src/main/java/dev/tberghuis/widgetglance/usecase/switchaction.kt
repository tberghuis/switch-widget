package dev.tberghuis.widgetglance.usecase

import android.content.Context
import dev.tberghuis.widgetglance.data.ServiceData
import dev.tberghuis.widgetglance.logd
import dev.tberghuis.widgetglance.provideHaHttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody


// return http status code
suspend fun postHaSwitchAction(context: Context, entityId: String, action: String): String? {
  logd("postHaSwitchAction entityId $entityId")

  try {
    val response = context.provideHaHttpClient().post("/api/services/switch/$action") {
      setBody(ServiceData(entityId))
    }
    logd("postHaSwitchAction response $response")
    return response.status.toString()
  } catch (e: Exception) {
    logd("error $e")
  }
  return null
}