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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A simple wrapper around a spannable to make it easier to work with. Each time you append some
 * text, you create a new {@link Spanalot.Piece} which you can modify the contents and spans
 * individually. You can also add and remove global spans which apply to all pieces.
 */
public class Spanalot implements Spanned {
    protected SpannableStringBuilder str = new SpannableStringBuilder();
    private List<Piece> pieces = new ArrayList<>();
    private Piece globalPiece = new Piece(0, 0);

    /**
     * Constructs a new {@code Spanalot} with the given global spans.
     *
     * @param spans the global spans
     */
    public Spanalot(Object... spans) {
        addGlobalSpans(spans);
    }

    /**
     * Appends a segment of text with the given spans and creates a backing {@link Spanalot.Piece}
     * that you can use to modify it. (see {@link #get(int)} and {@link #getAll()}) for modifying
     * created pieces).
     *
     * @param text  the text to append
     * @param spans the spans to apply to the text
     * @return
     */
    public Spanalot append(CharSequence text, Object... spans) {
        int start = str.length();
        int end = start + text.length();
        str.append(text);
        Piece piece = new Piece(start, end);
        piece.addSpans(spans);
        pieces.add(piece);
        return this;
    }

    /**
     * Returns the {@link Spanalot.Piece} at the given index.
     *
     * @param index the index
     * @return
     */
    public Piece get(int index) {
        return pieces.get(index);
    }

    /**
     * Returns all {@link Spanalot.Piece}'s.
     *
     * @return
     */
    public Collection<Piece> getAll() {
        return Collections.unmodifiableCollection(pieces);
    }

    /**
     * Adds a span to the entire {@code Spanalot}.
     *
     * @param span the span to add
     * @return
     */
    public Spanalot addGlobalSpan(Object span) {
        globalPiece.addSpan(span);
        return this;
    }

    /**
     * Adds multiple spans to the entire {@code Spanalot}
     *
     * @param spans the spans to add
     * @return
     */
    public Spanalot addGlobalSpans(Object... spans) {
        globalPiece.addSpans(spans);
        return this;
    }

    public Spanalot removeGlobalSpan(Object span) {
        globalPiece.removeSpan(span);
        return this;
    }

    public Spanalot removeGlobalSpans(Object... spans) {
        globalPiece.removeSpans(spans);
        return this;
    }

    public Spanalot setGlobalSpan(Object span) {
        globalPiece.setSpan(span);
        return this;
    }

    public Spanalot setGlobalSpans(Object... spans) {
        globalPiece.setSpans(spans);
        return this;
    }

    public Spanalot clearGlobalSpans() {
        globalPiece.clearSpans();
        return this;
    }

    public Spanalot clearAllSpans() {
        globalPiece.clearSpans();
        for (Piece piece : pieces) {
            piece.clearSpans();
        }
        return this;
    }

    public Spanalot clear() {
        str.clear();
        pieces.clear();
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

    /**
     * Returns the number of pieces stored in the {@code Spanalot}.
     *
     * @return the number of pieces
     */
    public int size() {
        return pieces.size();
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
     * A piece represents a section of the {@code Spanalot} that was added with
     * {@link #append(CharSequence, Object...)}. You can get a piece with {@link #get(int)} and
     * then modify it's contents and applied spans.
     */
    public class Piece {
        private int start;
        private int end;
        private List<Object> spans;

        private Piece(int start, int end) {
            this.start = start;
            this.end = end;
            this.spans = new ArrayList<>();
        }

        public Piece addSpan(Object span) {
            spans.add(span);
            str.setSpan(span, start, end, SPAN_INCLUSIVE_INCLUSIVE);
            return this;
        }

        public Piece addSpans(Object... spans) {
            Collections.addAll(this.spans, spans);
            for (Object span : spans) {
                str.setSpan(span, start, end, SPAN_INCLUSIVE_INCLUSIVE);
            }
            return this;
        }

        public Piece removeSpan(Object span) {
            spans.remove(span);
            str.removeSpan(span);
            return this;
        }

        public Piece removeSpans(Object... spans) {
            for (Object span : spans) {
                this.spans.remove(span);
            }
            for (Object span : spans) {
                str.removeSpan(span);
            }
            return this;
        }

        /**
         * Sets the given span, clearing out any existing ones.
         *
         * @param span the span to set
         * @return
         */
        public Piece setSpan(Object span) {
            clearSpans();
            addSpan(span);
            return this;
        }

        /**
         * Sets the given spans, clearing out any existing ones.
         *
         * @param spans the spans to set
         * @return
         */
        public Piece setSpans(Object... spans) {
            clearSpans();
            addSpans(spans);
            return this;
        }

        public Piece clearSpans() {
            for (Object span : spans) {
                str.removeSpan(span);
            }
            spans.clear();
            return this;
        }

        /**
         * Sets the text for this piece replacing the previous text, keeping any spans intact.
         *
         * @param text the text to set
         * @return
         */
        public Piece setText(CharSequence text) {
            tempRemoveSpans();
            int newEnd = start + text.length();
            int oldEnd = end;
            str.replace(start, end, text);
            end = newEnd;
            restoreSpans();

            // Need to update downstream positions
            for (int i = pieces.indexOf(this) + 1; i < pieces.size(); i++) {
                pieces.get(i).shift(newEnd - oldEnd);
            }

            return this;
        }

        /**
         * Sets the text and spans for the piece, replacing the previous text and spans.
         *
         * @param text  the text to set
         * @param spans the spans to set
         * @return
         */
        public Piece set(CharSequence text, Object... spans) {
            clearSpans();
            setText(text);
            addSpans(spans);
            return this;
        }

        /**
         * Appends the text to the piece, keeping any spans intact.
         *
         * @param text the text to append
         * @return
         */
        public Piece append(CharSequence text) {
            tempRemoveSpans();
            int newEnd = end + text.length();
            str.insert(end, text);
            end = newEnd;
            restoreSpans();

            // Need to update downstream positions
            for (int i = pieces.indexOf(this) + 1; i < pieces.size(); i++) {
                pieces.get(i).shift(text.length());
            }

            return this;
        }

        private void tempRemoveSpans() {
            for (Object span : spans) {
                str.removeSpan(span);
            }
        }

        private void restoreSpans() {
            for (Object span : spans) {
                str.setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        private void shift(int amount) {
            start += amount;
            end += amount;
        }
    }

    /**
     * A convenience function to create a new {@link android.text.style.SubscriptSpan}.
     *
     * @return
     */
    public static SubscriptSpan subscript() {
        return new SubscriptSpan();
    }

    /**
     * A convenience function to create a new {@link android.text.style.SuperscriptSpan}.
     *
     * @return
     */
    public static SuperscriptSpan superscript() {
        return new SuperscriptSpan();
    }

    /**
     * A convenience function to create a new {@link android.text.style.StrikethroughSpan}.
     *
     * @return
     */
    public static StrikethroughSpan strikethough() {
        return new StrikethroughSpan();
    }

    /**
     * A convenience function to create a new {@link android.text.style.UnderlineSpan}.
     *
     * @return
     */
    public static UnderlineSpan underline() {
        return new UnderlineSpan();
    }

    /**
     * A convenience function to create a new {@link android.text.style.StyleSpan}.
     *
     * @return
     */
    public static StyleSpan style(int style) {
        return new StyleSpan(style);
    }

    /**
     * A convenience function to create a new {@link android.text.style.AbsoluteSizeSpan}.
     *
     * @return
     */
    public static AbsoluteSizeSpan textSize(int textSize) {
        return new AbsoluteSizeSpan(textSize);
    }

    /**
     * A convenience function to create a new {@link android.text.style.RelativeSizeSpan}.
     *
     * @return
     */
    public static RelativeSizeSpan textSizeRelative(float textSizeRelative) {
        return new RelativeSizeSpan(textSizeRelative);
    }

    /**
     * A convenience function to create a new {@link android.text.style.ForegroundColorSpan}.
     *
     * @return
     */
    public static ForegroundColorSpan textColor(int color) {
        return new ForegroundColorSpan(color);
    }

    /**
     * A convenience function to create a new {@link android.text.style.BackgroundColorSpan}.
     *
     * @return
     */
    public static BackgroundColorSpan backgroundColor(int color) {
        return new BackgroundColorSpan(color);
    }
}
