package me.tatarka.spanalot;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.Locale;

/**
 * A simple wrapper around a spannable to make it easier to work with.
 */
public final class Spanalot implements Spanned {

    private final SpannableStringBuilder str = new SpannableStringBuilder();
    private ArrayList<Object> globalSpans;
    private boolean globalSpansApplied;

    /**
     * Constructs a new {@code Spanalot}.
     */
    public Spanalot(Object... spans) {
        if (spans.length != 0) {
            globalSpans = new ArrayList<>(spans.length);
            Collections.addAll(globalSpans, spans);
        }
    }

    /**
     * Constructs a new {@code Spanalot} with the text and spans. This is a convenience method for
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
        clearGlobalSpans();
        int start = str.length();
        int end = start + text.length();
        str.append(text);
        for (Object span : spans) {
            str.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    /**
     * Appends a segment of text formatted with the given format string as with {@link
     * String#format(String, Object...)}. However, all spans are preserved in the format args.
     *
     * @param format the format string
     * @param args   the format args
     */
    public Spanalot format(String format, Object... args) {
        return format(Locale.getDefault(), format, args);
    }

    /**
     * Appends a segment of text formatted with the given format string as with {@link
     * String#format(Locale, String, Object...)}. However, all spans are preserved in the format
     * args.
     *
     * @param format the format string
     * @param args   the format args
     */
    public Spanalot format(Locale locale, String format, Object... args) {
        if (locale == null) {
            throw new NullPointerException("locale == null");
        }
        if (format == null) {
            throw new NullPointerException("formatString == null");
        }
        clearGlobalSpans();
        FormatRecorder recorder = new FormatRecorder();
        Formatter formatter = new Formatter(recorder, locale);
        formatter.format(format, recorder.wrapArgs(args));
        return this;
    }

    private void ensureGlobalSpans() {
        if (globalSpans != null && !globalSpansApplied) {
            for (Object span : globalSpans) {
                str.setSpan(span, 0, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
            globalSpansApplied = true;
        }
    }

    private void clearGlobalSpans() {
        if (globalSpans != null && globalSpansApplied) {
            for (Object span : globalSpans) {
                str.removeSpan(span);
            }
            globalSpansApplied = false;
        }
    }

    @Override
    public <T> T[] getSpans(int start, int end, Class<T> type) {
        ensureGlobalSpans();
        return str.getSpans(start, end, type);
    }

    @Override
    public int getSpanStart(Object tag) {
        ensureGlobalSpans();
        return str.getSpanStart(tag);
    }

    @Override
    public int getSpanEnd(Object tag) {
        ensureGlobalSpans();
        return str.getSpanEnd(tag);
    }

    @Override
    public int getSpanFlags(Object tag) {
        ensureGlobalSpans();
        return str.getSpanFlags(tag);
    }

    @Override
    public int nextSpanTransition(int start, int limit, Class type) {
        ensureGlobalSpans();
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
        ensureGlobalSpans();
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

    /**
     * Records the locations of formatted strings so spans can be applied.
     */
    private class FormatRecorder implements Appendable {
        Spanned nextArg;

        FormatRecorder() {
        }

        Object[] wrapArgs(Object[] args) {
            Object[] newArgs = args;
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof Spanned) {
                    if (newArgs == args) {
                        newArgs = Arrays.copyOf(args, args.length);
                    }
                    newArgs[i] = new RecordingSpanned((Spanned) arg);
                }
            }
            return newArgs;
        }

        @Override
        public Appendable append(char c) {
            clearGlobalSpans();
            str.append(c);
            return this;
        }

        @Override
        public Appendable append(CharSequence csq) {
            clearGlobalSpans();
            if (nextArg != null) {
                int strStart = str.length();
                str.append(csq);
                TextUtils.copySpansFrom(nextArg, 0, csq.length(), null, str, strStart);
                nextArg = null;
            } else {
                str.append(csq);
            }
            return this;
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) {
            clearGlobalSpans();
            if (nextArg != null) {
                int strStart = str.length();
                str.append(csq, start, end);
                TextUtils.copySpansFrom(nextArg, start, end, null, str, strStart);
                nextArg = null;
            } else {
                str.append(csq, start, end);
            }
            return this;
        }

        private class RecordingSpanned {
            Spanned str;

            RecordingSpanned(Spanned str) {
                this.str = str;
            }

            @Override
            public String toString() {
                nextArg = str;
                return str.toString();
            }
        }
    }

}
