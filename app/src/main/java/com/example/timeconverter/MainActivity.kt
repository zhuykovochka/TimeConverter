package com.example.timeconverter

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputValue: EditText
    private lateinit var fromUnit: Spinner
    private lateinit var toUnit: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultText: TextView

    private val timeUnits = arrayOf("Секунды", "Минуты", "Часы", "Дни", "Недели", "Месяцы", "Годы", "Декады", "Век", "Тысячелетие")

    private val conversionRates = mapOf(
        "Секунды" to 1.0, "Минуты" to 60.0, "Часы" to 3600.0, "Дни" to 86400.0,
        "Недели" to 604800.0, "Месяцы" to 2592000.0, "Годы" to 31536000.0,
        "Декады" to 315360000.0, "Век" to 3153600000.0, "Тысячелетие" to 31536000000.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ПРОСТО ДОБАВЛЯЕМ ВЕРХНИЙ ОТСТУП К ЗАГОЛОВКУ
        val titleText = findViewById<android.widget.TextView>(R.id.title_text)
        titleText.setPadding(0, 75, 0, 0)

        // Находим элементы
        inputValue = findViewById(R.id.inputValue)
        fromUnit = findViewById(R.id.fromUnit)
        toUnit = findViewById(R.id.toUnit)
        convertButton = findViewById(R.id.convertButton)
        resultText = findViewById(R.id.resultText)

        // Настраиваем выпадающие списки
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeUnits)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        fromUnit.adapter = adapter
        toUnit.adapter = adapter

        // Обработка кнопки
        convertButton.setOnClickListener {
            convertTime()
        }

        // Автоконвертация при изменении единиц
        fromUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                convertTime()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        toUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                convertTime()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun convertTime() {
        try {
            val inputText = inputValue.text.toString()
            if (inputText.isEmpty()) return

            val value = inputText.toDouble()
            val fromRate = conversionRates[timeUnits[fromUnit.selectedItemPosition]] ?: 1.0
            val toRate = conversionRates[timeUnits[toUnit.selectedItemPosition]] ?: 1.0

            val result = value * fromRate / toRate
            resultText.text = if (result % 1 == 0.0) result.toInt().toString()
            else "%.4f".format(result).trimEnd('0').trimEnd('.')

        } catch (e: Exception) {
            resultText.text = "Ошибка"
        }
    }
}