package dev.tberghuis.widgetglance.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  vm: HomeScreenViewModel = viewModel()
) {
  val haBaseUrl = vm.haBaseUrl.collectAsState()
  val haAuthToken = vm.haAuthToken.collectAsState()

  Scaffold(
    modifier = Modifier,
    topBar = {
      TopAppBar(title = { Text("Switch Widget for HA") })
    },
    snackbarHost = { SnackbarHost(vm.snackbarHostState) },
  ) { padding ->
    Column(
      modifier = Modifier
        .padding(padding)
        .fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        "HA Server Config",
        modifier = Modifier.padding(bottom = 20.dp),
        style = MaterialTheme.typography.titleLarge
      )

      OutlinedTextField(
        value = haBaseUrl.value,
        onValueChange = { vm.haBaseUrl.value = it },
        modifier = Modifier.width(300.dp),
        label = { Text("HA Server URL") },
        supportingText = { Text("example: http://192.168.0.99:8123") },
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
        singleLine = true,
      )

      OutlinedTextField(
        value = haAuthToken.value,
        onValueChange = { vm.haAuthToken.value = it },
        modifier = Modifier
          .padding(top = 20.dp)
          .width(300.dp),
        label = { Text("Access Token") },
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
        singleLine = true,
      )
      Text(
        "Access Token: This can be created within Home Assistant /profile page under section \"Long-Lived Access Tokens\"",
        modifier = Modifier.width(300.dp),
      )

      Button(
        onClick = { vm.requestPinGlanceAppWidget() },
        modifier = Modifier.padding(top = 20.dp),
      ) {
        Text("Add Switch to Home Screen")
      }
    }
  }
}