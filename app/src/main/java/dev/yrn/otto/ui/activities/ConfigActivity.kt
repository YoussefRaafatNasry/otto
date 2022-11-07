package dev.yrn.otto.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yrn.otto.Config
import dev.yrn.otto.ui.theme.OttoTheme


class ConfigActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OttoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ConfigPage()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ConfigPage() {
    Column(
        Modifier
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text("Apps", style = TextStyle(fontWeight = FontWeight.Black))
        Column {
            Config.PACKAGES.map {
                Text(it)
            }
        }

        Text(
            "Rules",
            style = TextStyle(fontWeight = FontWeight.Black),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Config.RULES.map {
                Column {
                    Row {
                        Text(it.pattern)
                        if (it.command != null) {
                            Text(" ●")
                        }
                    }
                    it.replies.map {
                        Text(
                            "└ $it",
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.primary.copy(alpha = 0.64F)
                        )
                    }
                }

            }
        }
    }
}
