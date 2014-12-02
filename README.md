spanalot
========

A simple utility for creating and modifying spannables in Android

### Usage

```java
import me.tatarka.spanalot.Spanalot;
import static me.tatarka.spanalot.Spanalot.*;

// Construct a new spanalot with some global spans. 
// You can use the provided functions to simplify common spans.
Spanalot spanalot = new Spanalot(backgroundColor(getResources().getColor(R.color.red_200)))
        // Append segments of text with spanns that apply to them.
        .append("Hello, ", style(Typeface.ITALIC))
        .append("World!", textColor(getResources().getColor(R.color.purple_900)),
                          textSizeRelative(1.5f),
                          // You can use your own spans if you feel like it.
                          new MyCustomSpanThatDoesWhatever());

// Spanalot is just a Spanned, use it like one!
textView.setText(spanalot);

// You can get pieces that you appened and modify their contents and spans.
spanalot.get(0).setText("Hello 2,").addSpan(strikethrough());

// Make sure you call setText() again to apply you changes!
textView.setText(spanalot);
```

That's it! What could be simpler?
