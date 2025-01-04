package me.tatarka.spanalot.sample

import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import me.tatarka.spanalot.Spanalot
import me.tatarka.spanalot.append
import me.tatarka.spanalot.appendFormat
import me.tatarka.spanalot.buildAnnotatedString

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(stringResource(R.string.app_name))
                        })
                    }
                ) { padding ->
                    Column(modifier = Modifier.padding(padding)) {
                        AndroidView(
                            factory = { context ->
                                FrameLayout(context).apply {
                                    LayoutInflater.from(context)
                                        .inflate(R.layout.view_main, this, true)

                                    val helloWorld = findViewById<TextView>(R.id.hello_world)
                                    val helloWorld2 = findViewById<TextView>(R.id.hello_world2)
                                    val red = ContextCompat.getColor(context, R.color.red_200)
                                    val purple = ContextCompat.getColor(context, R.color.purple_900)

                                    helloWorld.text = Spanalot(Spanalot.backgroundColor(red))
                                        .append("Hello, ", Spanalot.style(Typeface.ITALIC))
                                        .append(
                                            "World!",
                                            Spanalot.underline(),
                                            Spanalot.textColor(purple),
                                            Spanalot.textSizeRelative(1.5f)
                                        )

                                    helloWorld2.text = Spanalot(Spanalot.backgroundColor(red))
                                        .format("%1\$s, %2\$s!")
                                        .arg("Hello", Spanalot.style(Typeface.ITALIC))
                                        .arg(Html.fromHtml("<b>World</b>"))
                                }
                            }
                        )

                        Column(modifier = Modifier.padding(16.dp)) {
                            val red = colorResource(R.color.red_200)
                            val purple = colorResource(R.color.purple_900)

                            Text(
                                text = buildAnnotatedString(SpanStyle(background = red)) {
                                    append("Hello, ", SpanStyle(fontStyle = FontStyle.Italic))
                                    append(
                                        "World!",
                                        SpanStyle(
                                            textDecoration = TextDecoration.Underline,
                                            color = purple,
                                            fontSize = 1.5.em
                                        )
                                    )
                                },
                                fontSize = 24.sp,
                                lineHeight = 40.sp,
                            )

                            Text(
                                text = buildAnnotatedString(SpanStyle(background = red)) {
                                    appendFormat(
                                        "%1\$s, %2\$s!",
                                        AnnotatedString(
                                            "Hello",
                                            SpanStyle(fontStyle = FontStyle.Italic)
                                        ),
                                        AnnotatedString.fromHtml("<b>World</b>")
                                    )
                                },
                                fontSize = 24.sp,
                                lineHeight = 30.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}
