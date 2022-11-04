package com.youssefraafatnasry.otto.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.youssefraafatnasry.otto.adapters.RulesAdapter
import com.youssefraafatnasry.otto.databinding.ActivityRulesBinding
import com.youssefraafatnasry.otto.util.Config

class RulesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRulesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRulesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rulesRecyclerView.adapter = RulesAdapter(Config.RULES)
        binding.rulesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

}
