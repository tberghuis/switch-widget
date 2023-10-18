package dev.tberghuis.widgetglance.tmp

import android.app.Application
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

  val fsdfsd = "fdsfsd"

  init {
    logd("TmpVm init savedStateHandle $savedStateHandle")
    logd("TmpVm init entityId ${savedStateHandle.get<String>("entityId")}")
    logd("TmpVm init action ${savedStateHandle.get<String>("action")}")

    performAction()
  }

  private fun performAction() {
    viewModelScope.launch(IO) {
      val entityId = savedStateHandle.get<String>("entityId")
      val action = savedStateHandle.get<String>("action")
      postHaSwitchAction(application, entityId!!, action!!)
    }
  }
}