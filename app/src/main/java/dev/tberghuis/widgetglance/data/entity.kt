package dev.tberghuis.widgetglance.data

import kotlinx.serialization.Serializable

// this represents data from home assistant /api/states
@Serializable
data class Entity(val entity_id: String)

@Serializable
data class ServiceData(val entity_id: String)

@Serializable
data class SwitchStateResponse(val state: String)