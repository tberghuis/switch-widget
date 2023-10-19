package dev.tberghuis.widgetglance.tmp

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.tberghuis.widgetglance.logd
import dev.tberghuis.widgetglance.usecase.postHaSwitchAction
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TmpVm(
  private val application: Application,
  private val savedStateHandle: SavedStateHandle
) :
  AndroidViewModel(application) {
  var entityId by mutableStateOf("")
  var action by mutableStateOf("")
  var switchName: String? by mutableStateOf(null)

  init {
    logd("TmpVm init savedStateHandle $savedStateHandle")
    logd("TmpVm init entityId ${savedStateHandle.get<String>("entityId")}")
    logd("TmpVm init action ${savedStateHandle.get<String>("action")}")

    assignDeepLinkParamsToState()
    performAction()
  }

  private fun assignDeepLinkParamsToState() {
    entityId = savedStateHandle.get<String>("entityId")!!
    action = savedStateHandle.get<String>("action")!!
    switchName = savedStateHandle.get<String>("switchName")
  }


  private fun performAction() {
    viewModelScope.launch(IO) {
      val entityId = savedStateHandle.get<String>("entityId")
      val action = savedStateHandle.get<String>("action")
      postHaSwitchAction(application, entityId!!, action!!)
    }
  }
}