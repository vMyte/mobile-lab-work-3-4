package com.example.firstlab

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val sourceText = "Это заранее подготовленный текст для проверки " +
            "корректой работы данного одноэкранного мобильного приложения, " +
            "благодаря которому я познакомился с новыми технологиями"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lengthInput: EditText = findViewById(R.id.editTextText2)
        val processButton: Button = findViewById(R.id.button)
        val resultTextView: TextView = findViewById(R.id.textView)

        resultTextView.text = sourceText

        val textProcessor = TextProcessor()

        processButton.setOnClickListener {
            val lengthStr = lengthInput.text.toString()

            if (lengthStr.isNotEmpty()) {
                    val lengthToRemove = lengthStr.toInt()
                    val processedText = textProcessor.processText(sourceText, lengthToRemove)
                    resultTextView.text = processedText
            } else {
                Toast.makeText(this, "Поле для ввода длины не должно быть пустым!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
