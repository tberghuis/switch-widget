package dev.tberghuis.widgetglance.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import dev.tberghuis.widgetglance.logd
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoCompleteText(
  value: String,
  onValueChange: (String) -> Unit,
  onOptionSelected: (String) -> Unit,
  modifier: Modifier = Modifier,
  label: @Composable (() -> Unit)? = null,
  suggestions: List<String> = emptyList()
) {
  var textFieldFocused by remember {
    mutableStateOf(false)
  }
  val scope = rememberCoroutineScope()

  Column(
    modifier = modifier
  ) {
    OutlinedTextField(
      value = value,
      onValueChange = { text -> if (text !== value) onValueChange(text) },
      modifier = Modifier
        .fillMaxWidth()
        .onFocusChanged {
          logd("onFocusChanged $it")
          scope.launch {
            // prevent bug where DropdownMenu overlaps soft keyboard
            delay(500)
            textFieldFocused = it.isFocused
          }
        },
      label = label,
    )

    DropdownMenu(
      expanded = textFieldFocused && suggestions.isNotEmpty(),
      onDismissRequest = { },
      modifier = Modifier.width(300.dp),
      // This line here will accomplish what you want
      properties = PopupProperties(focusable = false)
    ) {
      suggestions.forEach { label ->
        DropdownMenuItem(
          text = { Text(text = label) },
          onClick = {
            onOptionSelected(label)
          },
        )
      }
    }
  }
}