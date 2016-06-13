package me.tatarka.spanalot.sample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        Spanalot spanalot = new Spanalot(backgroundColor(getResources().getColor(R.color.red_200)))
                .append("Hello, ", style(Typeface.ITALIC))
                .append("World!", underline(), textColor(getResources().getColor(R.color.purple_900)), textSizeRelative(1.5f));

        helloWorld.setText(spanalot);
    }
}
