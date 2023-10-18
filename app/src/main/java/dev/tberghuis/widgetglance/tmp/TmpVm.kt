package dev.tberghuis.widgetglance.tmp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import dev.tberghuis.widgetglance.logd

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
  }
}