package dev.tberghuis.widgetglance.tmp

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dev.tberghuis.widgetglance.logd
import dev.tberghuis.widgetglance.ui.theme.WidgetGlanceTheme


class TmpActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)


    val action: String? = intent?.action
    val data: Uri? = intent?.data
    logd("TmpActivity onCreate action: $action")
    logd("TmpActivity onCreate data: $data")

    setContent {
      WidgetGlanceTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          TmpScreen()
        }
      }
    }
  }
}