package me.tatarka.spanalot.sample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import me.tatarka.spanalot.Spanalot;

import static me.tatarka.spanalot.Spanalot.backgroundColor;
import static me.tatarka.spanalot.Spanalot.style;
import static me.tatarka.spanalot.Spanalot.textColor;
import static me.tatarka.spanalot.Spanalot.textSizeRelative;
import static me.tatarka.spanalot.Spanalot.underline;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView helloWorld = (TextView) findViewById(R.id.hello_world);
        TextView helloWorld2 = (TextView) findViewById(R.id.hello_world2);
        int red = ContextCompat.getColor(this, R.color.red_200);
        int purple = ContextCompat.getColor(this, R.color.purple_900);

        Spanalot spanalot = new Spanalot(backgroundColor(red))
                .append("Hello, ", style(Typeface.ITALIC))
                .append("World!", underline(), textColor(purple), textSizeRelative(1.5f));

        helloWorld.setText(spanalot);

        Spanalot spanalot2 = new Spanalot(backgroundColor(red))
                .format("%1$s, %2$s!")
                .arg("Hello", style(Typeface.ITALIC))
                .arg(Html.fromHtml("<b>World</b>"));

        helloWorld2.setText(spanalot2);
    }
}
