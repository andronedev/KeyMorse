package dev.androne.keymorse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonClick = findViewById<Button>(R.id.changeKeyboard)
        val imeManager: InputMethodManager =
            applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager;

        buttonClick.setOnClickListener {
            imeManager.showInputMethodPicker();
        }
    }

}