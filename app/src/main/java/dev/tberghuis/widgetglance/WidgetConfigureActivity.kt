package dev.tberghuis.widgetglance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dev.tberghuis.widgetglance.screens.WidgetConfigureScreen
import dev.tberghuis.widgetglance.ui.theme.WidgetGlanceTheme

class WidgetConfigureActivity : ComponentActivity() {

//  var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

//    appWidgetId = intent?.extras?.getInt(
//      AppWidgetManager.EXTRA_APPWIDGET_ID,
//      AppWidgetManager.INVALID_APPWIDGET_ID
//    ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
//    logd("appWidgetId $appWidgetId")
//    if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
//      setResult(RESULT_CANCELED)
//      finish()
//    }

    setContent {
      WidgetGlanceTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          WidgetConfigureScreen()
        }
      }
    }
  }
}