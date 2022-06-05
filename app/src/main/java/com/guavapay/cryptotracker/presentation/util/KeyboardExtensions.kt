package com.guavapay.cryptotracker.presentation.util

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

object KeyboardExtensions {
    fun View.keyboardVisibilityChanges(): Flow<Boolean> {
        return onPreDrawFlow()
            .map { isKeyboardVisible() }
            .distinctUntilChanged()
    }

    private fun View.onPreDrawFlow(): Flow<Unit> {
        return callbackFlow {
            val onPreDrawListener = ViewTreeObserver.OnPreDrawListener {
                trySendBlocking(Unit)
                true
            }
            viewTreeObserver.addOnPreDrawListener(onPreDrawListener)
            awaitClose {
                viewTreeObserver.removeOnPreDrawListener(onPreDrawListener)
            }
        }
    }

    private fun View.isKeyboardVisible(): Boolean = ViewCompat.getRootWindowInsets(this)
        ?.isVisible(WindowInsetsCompat.Type.ime())
        ?: false

    fun View.focusAndShowKeyboard() {
        /**
         * This is to be called when the window already has focus.
         */
        fun View.showTheKeyboardNow() {
            if (isFocused) {
                post {
                    // We still post the call, just in case we are being notified of the windows focus
                    // but InputMethodManager didn't get properly setup yet.
                    val imm =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                }
            }
        }

        requestFocus()
        showTheKeyboardNow()
        // We need to wait until the window gets focus.
        viewTreeObserver.addOnWindowFocusChangeListener(
            object : ViewTreeObserver.OnWindowFocusChangeListener {
                override fun onWindowFocusChanged(hasFocus: Boolean) {
                    // This notification will arrive just before the InputMethodManager gets set up.
                    this@focusAndShowKeyboard.showTheKeyboardNow()
                    // It’s very important to remove this listener once we are done.
                    viewTreeObserver.removeOnWindowFocusChangeListener(this)
                }
            })
    }
}