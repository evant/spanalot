package me.tatarka.spanalot;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;

/**
 * A simple wrapper around a spannable to make it easier to work with.
 */
public class Spanalot implements Spanned {
    protected SpannableStringBuilder str = new SpannableStringBuilder();

    /**
     * Constructs a new {@code Spanalot}.
     */
    public Spanalot() {
    }

    /**
     * Constructs a new {@code Spanalot} with the text and spans.
     * This is a convenience method for
     * {@code new Spanalot().append(CharSequence, Object... spans)} for cases where you only have
     * one text segment.
     *
     * @param text  the text to append
     * @param spans the spans to add
     */
    public Spanalot(CharSequence text, Object... spans) {
        append(text, spans);
    }

    /**
     * Appends a segment of text with the given spans.
     *
     * @param text  the text to append
     * @param spans the spans to apply to the text
     */
    public Spanalot append(CharSequence text, Object... spans) {
        int start = str.length();
        int end = start + text.length();
        str.append(text);
        for (Object span : spans) {
            str.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    @Override
    public <T> T[] getSpans(int start, int end, Class<T> type) {
        return str.getSpans(start, end, type);
    }

    @Override
    public int getSpanStart(Object tag) {
        return str.getSpanStart(tag);
    }

    @Override
    public int getSpanEnd(Object tag) {
        return str.getSpanEnd(tag);
    }

    @Override
    public int getSpanFlags(Object tag) {
        return str.getSpanFlags(tag);
    }

    @Override
    public int nextSpanTransition(int start, int limit, Class type) {
        return str.nextSpanTransition(start, limit, type);
    }

    @Override
    public int length() {
        return str.length();
    }

    @Override
    public char charAt(int index) {
        return str.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return str.subSequence(start, end);
    }

    @Override
    @NonNull
    public String toString() {
        return str.toString();
    }

    /**
     * A convenience function to create a new {@link android.text.style.SubscriptSpan}.
     */
    public static SubscriptSpan subscript() {
        return new SubscriptSpan();
    }

    /**
     * A convenience function to create a new {@link android.text.style.SuperscriptSpan}.
     */
    public static SuperscriptSpan superscript() {
        return new SuperscriptSpan();
    }

    /**
     * A convenience function to create a new {@link android.text.style.StrikethroughSpan}.
     */
    public static StrikethroughSpan strikethough() {
        return new StrikethroughSpan();
    }

    /**
     * A convenience function to create a new {@link android.text.style.UnderlineSpan}.
     */
    public static UnderlineSpan underline() {
        return new UnderlineSpan();
    }

    /**
     * A convenience function to create a new {@link android.text.style.StyleSpan}.
     */
    public static StyleSpan style(int style) {
        return new StyleSpan(style);
    }

    /**
     * A convenience function to create a new {@link android.text.style.AbsoluteSizeSpan}.
     */
    public static AbsoluteSizeSpan textSize(int textSize) {
        return new AbsoluteSizeSpan(textSize);
    }

    /**
     * A convenience function to create a new {@link android.text.style.RelativeSizeSpan}.
     */
    public static RelativeSizeSpan textSizeRelative(float textSizeRelative) {
        return new RelativeSizeSpan(textSizeRelative);
    }

    /**
     * A convenience function to create a new {@link android.text.style.ForegroundColorSpan}.
     */
    public static ForegroundColorSpan textColor(int color) {
        return new ForegroundColorSpan(color);
    }

    /**
     * A convenience function to create a new {@link android.text.style.BackgroundColorSpan}.
     */
    public static BackgroundColorSpan backgroundColor(int color) {
        return new BackgroundColorSpan(color);
    }
}
