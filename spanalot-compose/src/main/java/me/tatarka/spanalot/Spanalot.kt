package me.tatarka.spanalot

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import java.util.Formatter
import java.util.Locale

@Suppress("NOTHING_TO_INLINE")
inline fun AnnotatedString.Builder.append(
    text: CharSequence,
    paragraphStyle: ParagraphStyle
) {
    pushStyle(paragraphStyle)
    append(text)
    pop()
}

@Suppress("NOTHING_TO_INLINE")
inline fun AnnotatedString.Builder.append(
    text: CharSequence,
    spanStyle: SpanStyle,
    paragraphStyle: ParagraphStyle? = null,
) {
    val index = pushStyle(spanStyle)
    if (paragraphStyle != null) {
        pushStyle(paragraphStyle)
    }
    append(text)
    pop(index)
}

inline fun buildAnnotatedString(
    paragraphStyle: ParagraphStyle,
    builder: AnnotatedString.Builder.() -> Unit
): AnnotatedString = buildAnnotatedString {
    pushStyle(paragraphStyle)
    builder()
}

inline fun buildAnnotatedString(
    spanStyle: SpanStyle,
    paragraphStyle: ParagraphStyle? = null,
    builder: AnnotatedString.Builder.() -> Unit
): AnnotatedString = buildAnnotatedString {
    pushStyle(spanStyle);
    if (paragraphStyle != null) {
        pushStyle(paragraphStyle)
    }
    builder()
}

fun AnnotatedString.Companion.format(
    locale: Locale?,
    formatString: String,
    vararg args: Any?,
): AnnotatedString = buildAnnotatedString {
    val handler = FormatHandler(this, args)
    val formatter = Formatter(handler, locale)
    formatter.format(formatString, *handler.args)
}

fun AnnotatedString.Companion.format(
    formatString: String,
    vararg args: Any?,
): AnnotatedString = buildAnnotatedString {
    val handler = FormatHandler(this, args)
    val formatter = Formatter(handler)
    formatter.format(formatString, *handler.args)
}

fun AnnotatedString.Builder.appendFormat(
    locale: Locale?,
    formatString: String,
    vararg args: Any?,
) {
    val handler = FormatHandler(this, args)
    val formatter = Formatter(handler, locale)
    formatter.format(formatString, *handler.args)
}

fun AnnotatedString.Builder.appendFormat(
    formatString: String,
    vararg args: Any?,
) {
    val handler = FormatHandler(this, args)
    val formatter = Formatter(handler)
    formatter.format(formatString, *handler.args)
}

private class FormatHandler(
    private val builder: AnnotatedString.Builder,
    args: Array<out Any?>,
) : Appendable {
    val args = Array(args.size) { index ->
        val arg = args[index]
        if (arg is AnnotatedString) RecordingArg(arg) else arg
    }
    var nextArg: AnnotatedString? = null

    override fun append(csq: CharSequence?): Appendable = apply {
        if (!consumeNextArg(builder)) {
            builder.append(csq)
        }
    }

    override fun append(csq: CharSequence?, start: Int, end: Int): Appendable = apply {
        if (!consumeNextArg(builder)) {
            builder.append(csq, start, end)
        }
    }

    override fun append(c: Char): Appendable = apply {
        builder.append(c)
    }

    private fun consumeNextArg(builder: AnnotatedString.Builder): Boolean {
        val next = nextArg
        if (next == null) {
            return false
        } else {
            nextArg = null
            builder.append(next)
            return true
        }
    }

    private inner class RecordingArg(val value: AnnotatedString?) {

        override fun toString(): String {
            nextArg = value
            return value.toString()
        }
    }
}