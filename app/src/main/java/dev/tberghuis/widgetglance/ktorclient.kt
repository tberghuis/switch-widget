package dev.tberghuis.widgetglance

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHaHttpClient(authToken: String, baseUrl: String): HttpClient {
  return HttpClient(CIO) {
    install(ContentNegotiation) {
      json(Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
      })
    }

    defaultRequest {
      url(baseUrl)
      header(HttpHeaders.Authorization, "Bearer $authToken")
      contentType(ContentType.Application.Json)
    }
  }
}