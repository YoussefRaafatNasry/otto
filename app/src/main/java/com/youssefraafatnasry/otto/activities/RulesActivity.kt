package com.youssefraafatnasry.otto.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.youssefraafatnasry.otto.adapters.RulesAdapter
import com.youssefraafatnasry.otto.util.Config
import kotlinx.android.synthetic.main.activity_rules.*

class RulesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.youssefraafatnasry.otto.R.layout.activity_rules)
        rules_recycler_view.adapter = RulesAdapter(Config.RULES)
        rules_recycler_view.layoutManager = LinearLayoutManager(this)
    }

}
