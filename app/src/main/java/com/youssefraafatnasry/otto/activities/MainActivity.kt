package com.youssefraafatnasry.otto.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youssefraafatnasry.otto.R
import com.youssefraafatnasry.otto.util.SpotifyAPI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SpotifyAPI.authenticateSpotify(this)

        main_linear_layout.setOnLongClickListener {
            val intent = Intent(this@MainActivity, DebuggerActivity::class.java)
            startActivity(intent)
            return@setOnLongClickListener true
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        SpotifyAPI.initAccessToken(requestCode, resultCode, intent)
    }

}
