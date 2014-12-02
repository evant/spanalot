package me.tatarka.spanalot.sample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import me.tatarka.spanalot.Spanalot;
import me.tatarka.spanalot.sample.holdr.Holdr_ActivityMain;

import static me.tatarka.spanalot.Spanalot.backgroundColor;
import static me.tatarka.spanalot.Spanalot.style;
import static me.tatarka.spanalot.Spanalot.textColor;
import static me.tatarka.spanalot.Spanalot.textSizeRelative;
import static me.tatarka.spanalot.Spanalot.underline;


public class MainActivity extends ActionBarActivity {
    Holdr_ActivityMain holdr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        holdr = new Holdr_ActivityMain(findViewById(android.R.id.content));

        Spanalot spanalot = new Spanalot(backgroundColor(getResources().getColor(R.color.red_200)))
                .append("Hello, ", style(Typeface.ITALIC))
                .append("World!", underline(), textColor(getResources().getColor(R.color.purple_900)), textSizeRelative(1.5f));

        holdr.helloWorld.setText(spanalot);
    }
}
