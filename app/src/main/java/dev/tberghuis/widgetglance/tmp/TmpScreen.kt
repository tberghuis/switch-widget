package dev.tberghuis.widgetglance.tmp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink

@Composable
fun TmpScreen(
  vm: TmpVm = viewModel()
) {
  Column {
    Text("Pool ON") // from optional parameter ?command_name=
    Text("LOADING...")
    Text("result: OK")
    Text("vm ${vm.fsdfsd}")
  }
}


@Composable
fun MyNavHost() {
  val navController = rememberNavController()
  NavHost(navController, "switch_action") {
    composable(
      "switch_action",
      deepLinks = listOf(navDeepLink {
        uriPattern = "https://switch-widget-tmp.tberghuis.dev/{entityId}/{action}"
      },



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
