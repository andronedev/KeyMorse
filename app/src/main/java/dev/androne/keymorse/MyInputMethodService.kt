package dev.androne.keymorse

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.inputmethodservice.KeyboardView
import dev.androne.keymorse.R
import android.inputmethodservice.Keyboard
import android.view.inputmethod.InputConnection
import dev.androne.keymorse.MyInputMethodService
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager

class MyInputMethodService : InputMethodService(), OnKeyboardActionListener {
    override fun onCreateInputView(): View {
        // get the KeyboardView and add our Keyboard layout to it
        val keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as KeyboardView
        val keyboard = Keyboard(this, R.xml.number_pad)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)
        return keyboardView
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray) {
        val ic = currentInputConnection ?: return
        when (primaryCode) {
            changeKeyboard -> {
                val imeManager: InputMethodManager =
                    applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager;
                imeManager.showInputMethodPicker();
            }
            enter -> {
                ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            }
            Keyboard.KEYCODE_DELETE -> {
                val selectedText = ic.getSelectedText(0)
                if (TextUtils.isEmpty(selectedText)) {
                    // no selection, so delete previous character
                    ic.deleteSurroundingText(1, 0)
                } else {
                    // delete the selection
                    ic.commitText("", 1)
                }
            }
            else -> {
                val code = primaryCode.toChar()
                ic.commitText(code.toString(), 1)
            }
        }
    }

    override fun onPress(primaryCode: Int) {}
    override fun onRelease(primaryCode: Int) {}
    override fun onText(text: CharSequence) {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeDown() {}
    override fun swipeUp() {}

    companion object {
        const val enter = -10
        const val changeKeyboard = -15
    }
}