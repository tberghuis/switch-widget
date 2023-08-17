package dev.tberghuis.widgetglance.glance

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceComposable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.Switch
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import dev.tberghuis.widgetglance.R
import dev.tberghuis.widgetglance.logd

class SwitchWidget : GlanceAppWidget() {
  override suspend fun provideGlance(context: Context, id: GlanceId) {
    logd("SwitchWidget provideGlance GlanceId $id")
    provideContent {
      SwitchWidgetContent()
    }
  }
}

@Composable
@GlanceComposable
fun SwitchWidgetContent(
) {
  val prefs = currentState<Preferences>()
  val isConfigured = prefs[booleanPreferencesKey("is_configured")]
  val isOn = prefs[booleanPreferencesKey("is_on")]
  val entityId = prefs[stringPreferencesKey("entity_id")]
  val label = prefs[stringPreferencesKey("label")]

  GlanceTheme {
    Column(
      modifier = GlanceModifier.fillMaxSize()
        .background(GlanceTheme.colors.surface)
        .cornerRadius(16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      if (isConfigured != true) {
//        todo replace with circular spinner
        Text("LOADING ...")
        return@Column
      }
      Text(label!!)
      Row(
        modifier = GlanceModifier.padding(top = 10.dp),
      ) {
        Image(
          provider = ImageProvider(R.drawable.refresh),
          contentDescription = "refresh",
          modifier = GlanceModifier.clickable(
            actionRunCallback<RefreshClickAction>(
              actionParametersOf(entityIdKey to entityId!!)
            )
          ),
        )
        Switch(
          checked = isOn == true,
          onCheckedChange = actionRunCallback<SwitchClickAction>(
            actionParametersOf(entityIdKey to entityId!!)
          ),
          modifier = GlanceModifier.padding(start = 15.dp),
        )
      }
    }
  }
}

//@Composable
//fun SwitchBox() {
//  val prefs = currentState<Preferences>()
//  val isOn = prefs[booleanPreferencesKey("is_on")]
//
//  Row(
//    modifier = GlanceModifier.clickable(actionRunCallback<SwitchBoxClickAction>()),
//  ) {
//    Text("ON")
//    Switch(
//      checked = isOn == true,
//      onCheckedChange = null,
//    )
//  }
//}
