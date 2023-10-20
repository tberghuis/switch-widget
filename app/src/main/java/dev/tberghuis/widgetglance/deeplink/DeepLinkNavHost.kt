package dev.tberghuis.widgetglance.deeplink

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink

@Composable
fun DeepLinkNavHost() {
  val navController = rememberNavController()
  NavHost(navController, "switch_action") {
    composable(
      "switch_action",
      deepLinks = listOf(
        navDeepLink {
          uriPattern =
//            "https://switch-widget-tmp.tberghuis.dev/{entityId}/{action}?switch_name={switchName}"
          "https://switchwidgetforha.tberghuis.dev/{entityId}/{action}?switch_name={switchName}"
        }
      ),
    ) {
      DeepLinkScreen()
    }
  }
}