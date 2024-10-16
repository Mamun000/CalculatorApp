package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Declare your TextView and MaterialButton variables
    private lateinit var resultTv: TextView
    private lateinit var solutionTv: TextView
    private lateinit var buttonC: MaterialButton
    private lateinit var buttonOb: MaterialButton
    private lateinit var buttonCb: MaterialButton
    private lateinit var buttonDe: MaterialButton
    private lateinit var button7: MaterialButton
    private lateinit var button8: MaterialButton
    private lateinit var button9: MaterialButton
    private lateinit var buttonMu: MaterialButton
    private lateinit var button4: MaterialButton
    private lateinit var button5: MaterialButton
    private lateinit var button6: MaterialButton
    private lateinit var buttonMi: MaterialButton
    private lateinit var button1: MaterialButton
    private lateinit var button2: MaterialButton
    private lateinit var button3: MaterialButton
    private lateinit var buttonPl: MaterialButton
    private lateinit var buttonAc: MaterialButton
    private lateinit var button0: MaterialButton
    private lateinit var buttonDot: MaterialButton
    private lateinit var buttonEqu: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize the TextViews
        resultTv = findViewById(R.id.result_tv)
        solutionTv = findViewById(R.id.solution_tv)

        // Initialize the buttons
        buttonC = findViewById(R.id.button_c)
        buttonOb = findViewById(R.id.button_ob)
        buttonCb = findViewById(R.id.button_cb)
        buttonDe = findViewById(R.id.button_devide)
        button7 = findViewById(R.id.button_7)
        button8 = findViewById(R.id.button_8)
        button9 = findViewById(R.id.button_9)
        buttonMu = findViewById(R.id.button_multiply)
        button4 = findViewById(R.id.button_4)
        button5 = findViewById(R.id.button_5)
        button6 = findViewById(R.id.button_6)
        buttonMi = findViewById(R.id.button_minus)
        button1 = findViewById(R.id.button_1)
        button2 = findViewById(R.id.button_2)
        button3 = findViewById(R.id.button_3)
        buttonPl = findViewById(R.id.button_plus)
        buttonAc = findViewById(R.id.button_AC)
        button0 = findViewById(R.id.button_0)
        buttonDot = findViewById(R.id.button_dot)
        buttonEqu = findViewById(R.id.button_equals)

        // Set click listeners for all buttons
        setButtonListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Method to set click listeners for all buttons
    private fun setButtonListeners() {
        val buttons = listOf(
            buttonC, buttonOb, buttonCb, buttonDe, button7, button8, button9, buttonMu,
            button4, button5, button6, buttonMi, button1, button2, button3, buttonPl,
            buttonAc, button0, buttonDot, buttonEqu
        )

        for (button in buttons) {
            button.setOnClickListener(this)
        }
    }

    // Handle button clicks
    override fun onClick(view: View) {
        val button = view as MaterialButton
        val buttonText = button.text.toString()

        if (buttonText == "AC") {
            solutionTv.text = ""
            resultTv.text = "0"
            return
        }

        if (buttonText == "=") {
            val expression = solutionTv.text.toString()
            val result = getResult(expression)
            resultTv.text = result
            return
        }

        if (buttonText == "C") {
            val currentText = solutionTv.text.toString()
            if (currentText.isNotEmpty()) {
                solutionTv.text = currentText.dropLast(1)
            }
        } else {
            solutionTv.append(buttonText)
        }
    }

    // Function to evaluate the expression using Rhino (JavaScript engine)
    private fun getResult(data: String): String {
        return try {
            val context: Context = Context.enter()
            context.optimizationLevel = -1
            val scriptable: Scriptable = context.initStandardObjects()
            var finalResult = context.evaluateString(scriptable, data, "JavaScript", 1, null).toString()
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "")
            }
            finalResult
        } catch (e: Exception) {
            "Err"
        }
    }
}
