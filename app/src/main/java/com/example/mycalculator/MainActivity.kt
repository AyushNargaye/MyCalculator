package com.example.mycalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false
    private var tvInput: TextView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                val result = when {
                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")
                        val one = prefix + splitValue[0]
                        val two = splitValue[1]
                        one.toDouble() - two.toDouble()
                    }
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")
                        val one = prefix + splitValue[0]
                        val two = splitValue[1]
                        one.toDouble() + two.toDouble()
                    }
                    tvValue.contains("*") -> {
                        val splitValue = tvValue.split("*")
                        val one = prefix + splitValue[0]
                        val two = splitValue[1]
                        one.toDouble() * two.toDouble()
                    }
                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")
                        val one = prefix + splitValue[0]
                        val two = splitValue[1]
                        one.toDouble() / two.toDouble()
                    }
                    else -> return
                }

                tvInput?.text = removeZeroAfterDot(result.toString())

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        return if (result.contains(".0")) {
            result.substring(0, result.length - 2)
        } else {
            result
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
    }
}
