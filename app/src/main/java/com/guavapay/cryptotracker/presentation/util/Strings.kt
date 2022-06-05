package com.guavapay.cryptotracker.presentation.util

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.util.PatternsCompat
import java.util.*

fun String.removeWhitespaces() = replace(" ", "")

fun CharSequence?.isValidEmail(): Boolean =
    !isNullOrEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()

fun String.addCountryCode() = "994$this"

fun String.addPlus() = "+$this"

fun String.addCharAtIndex(char: Char, index: Int) =
    StringBuilder(this).apply { insert(index, char) }.toString()

fun String.titleCaseFirstCharIfItIsLowercase() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

fun Int.inviteFriendPaymentCount(): String = "$this/3"

fun String.addBearer() = "Bearer $this"

fun String.addGoogleNavigation() = "google.navigation:q=$this"

fun TextView.setHtmlText(source: String?) {
    source?.let {
        this.text = HtmlCompat.fromHtml(source, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}