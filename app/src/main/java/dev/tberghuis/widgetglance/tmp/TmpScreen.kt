package dev.tberghuis.widgetglance.tmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink

@Composable
fun TmpScreen(
  vm: TmpVm = viewModel()
) {
  Column(
    modifier = Modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text("${vm.switchName ?: vm.entityId} ${vm.action}") // from optional parameter ?command_name=
    Text("result: ${vm.result}")
  }
}


@Composable
fun MyNavHost() {
  val navController = rememberNavController()
  NavHost(navController, "switch_action") {
    composable(
      "switch_action",
      deepLinks = listOf(
        navDeepLink {
          uriPattern =
            "https://switch-widget-tmp.tberghuis.dev/{entityId}/{action}?switch_name={switchName}"
        }
      ),
    ) { backStackEntry ->
//      val entityId = backStackEntry.arguments?.getString("entityId")
//      val action = backStackEntry.arguments?.getString("action")
//      Column {
//        Text("MyNavHost entityId $entityId action $action")
//      }
      TmpScreen()
    }
  }
}
