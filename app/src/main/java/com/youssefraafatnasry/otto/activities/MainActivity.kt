package com.youssefraafatnasry.otto.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youssefraafatnasry.otto.databinding.ActivityMainBinding
import com.youssefraafatnasry.otto.util.SpotifyAPI

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainLinearLayout.setOnLongClickListener {
            val intent = Intent(this@MainActivity, DebuggerActivity::class.java)
            startActivity(intent)
            return@setOnLongClickListener true
        }

        SpotifyAPI.authenticateSpotify(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        SpotifyAPI.initAccessToken(requestCode, resultCode, intent)
    }

}
