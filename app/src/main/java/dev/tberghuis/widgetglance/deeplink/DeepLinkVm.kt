package dev.tberghuis.widgetglance.deeplink

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.tberghuis.widgetglance.usecase.getHaSwitchState
import dev.tberghuis.widgetglance.usecase.postHaSwitchAction
import kotlinx.coroutines.launch

class DeepLinkVm(
  private val application: Application,
  private val savedStateHandle: SavedStateHandle
) :
  AndroidViewModel(application) {
  var entityId by mutableStateOf("")
  var action by mutableStateOf("")
  var switchName: String? by mutableStateOf(null)
  var result by mutableStateOf("")

  init {
    assignDeepLinkParamsToState()
    performAction()
  }

  private fun assignDeepLinkParamsToState() {
    entityId = savedStateHandle.get<String>("entityId")!!
    action = savedStateHandle.get<String>("action")!!
    switchName = savedStateHandle.get<String>("switchName")
  }

  private fun performAction() {
    viewModelScope.launch {
      when (action) {
        "status" -> {
          result = getHaSwitchState(application, entityId) ?: ""
        }

        "turn_on", "turn_off" -> {
          result = postHaSwitchAction(application, entityId, action) ?: ""
        }
      }
    }
  }
}