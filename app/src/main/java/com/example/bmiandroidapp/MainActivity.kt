package com.example.bmiandroidapp// com.example.bmiandroidapp.MainActivity.kt
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("DefaultLocale", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weightInput = findViewById<EditText>(R.id.weightInput)
        val heightInput = findViewById<EditText>(R.id.heightInput)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        calculateButton.setOnClickListener {
            val weightText = weightInput.text.toString()
            val heightText = heightInput.text.toString()

            if (weightText.isNotEmpty() && heightText.isNotEmpty()) {
                val weight = weightText.toDoubleOrNull()
                val height = heightText.toDoubleOrNull()

                if (weight != null && height != null && height > 0) {
                    val person = Person(weight, height)
                    val bmi = person.calculateBMI()
                    val bmiCategory = BMI.getCategory(bmi)

                    val message = when (bmiCategory) {
                        BMI.UNDERWEIGHT -> "You are underweight."
                        BMI.NORMAL -> "Your weight is normal."
                        BMI.OVERWEIGHT -> "You are overweight."
                    }

                    resultTextView.text = String.format("%.2f\n%s", bmi, message)
                    resultTextView.setTextColor(
                        when (bmiCategory) {
                            BMI.NORMAL -> getColor(R.color.green)
                            else -> getColor(R.color.red)
                        }
                    )
                } else {
                    resultTextView.text = "Please enter valid weight and height."
                    resultTextView.setTextColor(getColor(R.color.red))
                }
            } else {
                resultTextView.text = "Please enter weight and height."
                resultTextView.setTextColor(getColor(R.color.red))
            }
        }
    }
}

class Person(private val weight: Double, private val height: Double) {
    fun calculateBMI(): Double {
        return weight / (height * height)
    }
}

enum class BMI {
    UNDERWEIGHT, NORMAL, OVERWEIGHT;

    companion object {
        fun getCategory(bmi: Double): BMI {
            return when {
                bmi < 18.5 -> UNDERWEIGHT
                bmi in 18.5..24.9 -> NORMAL
                else -> OVERWEIGHT
            }
        }
    }
}


