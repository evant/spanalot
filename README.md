spanalot
========

A simple utility for creating and modifying spannables in Android

This may end up on maven central sometime in the future, in the mean time, it's just one class
so you can copy-and-paste it into your project.
https://github.com/evant/spanalot/blob/master/spanalot/src/main/java/me/tatarka/spanalot/Spanalot.java

### Usage

```java
import me.tatarka.spanalot.Spanalot;
import static me.tatarka.spanalot.Spanalot.*;

// Construct a new spanalot with some global spans. 
// You can use the provided functions to simplify common spans.
Spanalot spanalot = new Spanalot(backgroundColor(getResources().getColor(R.color.red_200)))
        // Append segments of text with spans that apply to them.
        .append("Hello, ", style(Typeface.ITALIC))
        .append("World!", textColor(getResources().getColor(R.color.purple_900)),
                          textSizeRelative(1.5f),
                          // You can use your own spans if you feel like it.
                          new MyCustomSpanThatDoesWhatever());

// Spanalot is just a Spanned, use it like one!
textView.setText(spanalot);

// If you just need a single piece, you can use a more convienent constructor
textView.setText(new Spanalot("Hello, World!", style(Typeface.ITALIC)));

// You can format like String.format() too. Unlike String.format() spans are preserved!
Spanalot spanalot = new Spanalot(backgroundColor(getResources().getColor(R.color.red_200)))
        .format("%1$s, %2$s!")
        .arg("Hello", style(Typeface.ITALIC))
        // Any styled CharSequence will work.
        .arg(Html.fromHtml("<b>World</b>"));
```

That's it! What could be simpler?
