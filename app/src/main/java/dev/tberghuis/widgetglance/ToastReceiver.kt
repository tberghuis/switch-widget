package dev.tberghuis.widgetglance

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

// todo will probably need to enable notifications for this to work???
class ToastReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {
    Toast.makeText(
      context,
      "error TODO",
      Toast.LENGTH_SHORT
    ).show()
  }
}

fun Context.sendBroadcastToastReceiver() {
  sendBroadcast(Intent(this, ToastReceiver::class.java))
}