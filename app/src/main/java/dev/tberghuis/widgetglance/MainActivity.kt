package dev.tberghuis.widgetglance

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.pm.ShortcutManagerCompat
import dev.tberghuis.widgetglance.screens.HomeScreen
import dev.tberghuis.widgetglance.ui.theme.WidgetGlanceTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

//    tmp(this)

    setContent {
      WidgetGlanceTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          HomeScreen()
        }
      }
    }
  }
}


//fun tmp(context: Context){
//  val count = ShortcutManagerCompat.getMaxShortcutCountPerActivity(context)
//  logd("MainActivity getMaxShortcutCountPerActivity $count")
//}