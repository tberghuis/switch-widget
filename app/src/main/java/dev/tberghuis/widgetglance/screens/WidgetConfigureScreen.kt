package dev.tberghuis.widgetglance.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tberghuis.widgetglance.WidgetConfigureActivity
import dev.tberghuis.widgetglance.composables.AutoCompleteText

@Composable
fun WidgetConfigureScreen(
  vm: WidgetConfigureScreenViewModel = viewModel()
) {
  val context = LocalContext.current
  val focusManager = LocalFocusManager.current

  Scaffold(
    snackbarHost = { SnackbarHost(vm.snackbarHostState) },
  ) { padding ->
    Column(
      modifier = Modifier
        .padding(padding)
        .fillMaxSize()
        .pointerInput(Unit) {
          detectTapGestures(onTap = {
            focusManager.clearFocus()
          })
        },
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text("Configure Switch Widget")
      SwitchLabel(vm)
//      if (vm.initialized.value) {
//        SelectEntity(vm)
//      }
      SelectEntity(vm)

      Button(onClick = {
        vm.finish(context as WidgetConfigureActivity)
        // updateAppWidgetState(context, glanceId) { prefs ->
      }) {
        Text("Add to Home")
      }
    }

  }


}

@Composable
fun SelectEntity(
  vm: WidgetConfigureScreenViewModel = viewModel()
) {
  val focusManager = LocalFocusManager.current
  val entityIdTextField = vm.entityIdTextField.collectAsState()

  AutoCompleteText(
    value = entityIdTextField.value,
    onValueChange = {
      vm.entityIdTextField.value = it
    },
    onOptionSelected = {
      vm.entityIdTextField.value = it
      focusManager.clearFocus()
    },
    modifier = Modifier.widthIn(0.dp, 300.dp),
    label = { Text("entity_id") },
    suggestions = vm.entitySuggestions.value
  )
}

@Composable
fun SwitchLabel(vm: WidgetConfigureScreenViewModel = viewModel()) {
  OutlinedTextField(
    value = vm.switchLabel.value,
    onValueChange = {
      vm.switchLabel.value = it
    },
    modifier = Modifier.width(300.dp),
    label = { Text("label") },
    singleLine = true,
  )
}