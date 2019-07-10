package com.youssefraafatnasry.otto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Allow Notification Listener
        val isListenerAllowed = NotificationManagerCompat
            .getEnabledListenerPackages(this)
            .contains(BuildConfig.APPLICATION_ID)

        if (!isListenerAllowed) {
            startActivity(Intent(android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        // Application active status
        Config.IS_ACTIVE = switch_activate.isChecked
        switch_activate.setOnCheckedChangeListener { _, b -> Config.IS_ACTIVE = b }

    }
}
