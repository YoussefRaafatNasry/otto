package com.youssefraafatnasry.otto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Application active status
        Config.IS_ACTIVE = switch_activate.isChecked
        switch_activate.setOnCheckedChangeListener { _, b -> Config.IS_ACTIVE = b }

    }
}
