package com.example.calc

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class calc_page : AppCompatActivity() {
    private var canAddOperation=false
    private var canAddDecimal=true


    lateinit var tv: TextView
    lateinit var button_0: Button
    lateinit var button_1: Button
    lateinit var button_2: Button
    lateinit var button_3: Button
    lateinit var button_4: Button
    lateinit var button_5: Button
    lateinit var button_6: Button
    lateinit var button_7: Button
    lateinit var button_8: Button
    lateinit var button_9: Button

    lateinit var input: TextView
    lateinit var output: TextView
    lateinit var button_clear: Button
    lateinit var backspace: Button
    lateinit var multiply: Button
    lateinit var button_minus: Button
    lateinit var button_plus: Button
    lateinit var dot: Button
    lateinit var button_equal: Button
    lateinit var division: Button





    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc_page)


        input = findViewById(R.id.input)
        output = findViewById(R.id.output)
        button_clear = findViewById(R.id.button_clear)
        button_0 = findViewById(R.id.button_0)
        button_1 = findViewById(R.id.button_1)
        button_2 = findViewById(R.id.button_2)
        button_3 = findViewById(R.id.button_3)
        button_4 = findViewById(R.id.button_4)
        button_5 = findViewById(R.id.button_5)
        button_6 = findViewById(R.id.button_6)
        button_7 = findViewById(R.id.button_7)
        button_8 = findViewById(R.id.button_8)
        button_9 = findViewById(R.id.button_9)
        backspace = findViewById(R.id.backspace)
        division = findViewById(R.id.division)
        multiply = findViewById(R.id.multiply)
        button_minus = findViewById(R.id.button_minus)
        button_plus = findViewById(R.id.button_plus)
        dot = findViewById(R.id.dot)
        button_equal = findViewById(R.id.button_equal)


        input.movementMethod=ScrollingMovementMethod()
        input.isActivated=true
        input.isPressed=true












        tv = findViewById(R.id.textView2)
        val username = intent.getStringExtra("Name")
        val welcome = "Hello $username"
        tv.setText(welcome)

        button_clear.setOnClickListener {

            input.text = ""
            output.text = ""


        }
        button_0.setOnClickListener {
            input.text = addInputText("0")
        }
        button_1.setOnClickListener {
            input.text = addInputText("1")
        }
        button_2.setOnClickListener {
            input.text = addInputText("2")
        }
        button_3.setOnClickListener {
            input.text = addInputText("3")
        }
        button_5.setOnClickListener {
            input.text = addInputText("5")
        }
        button_4.setOnClickListener {
            input.text = addInputText("4")
        }
        button_6.setOnClickListener {
            input.text = addInputText("6")
        }
        button_7.setOnClickListener {
            input.text = addInputText("7")
        }
        button_8.setOnClickListener {
            input.text = addInputText("8")
        }
        button_9.setOnClickListener {
            input.text = addInputText("9")
        }
        division.setOnClickListener {
            input.text = addInputText("/")

        }
        multiply.setOnClickListener {
            input.text = addInputText("X")
        }
        button_plus.setOnClickListener {
            input.text = addInputText("+")




        }
        button_minus.setOnClickListener {
            input.text = addInputText("-")
        }
        dot.setOnClickListener {
            input.text = addInputText(".")
        }
        button_equal.setOnClickListener {
            output.text=calculateResults()




        }


    }

    private fun addInputText(buttonValue: String): String {

        return "${input.text}$buttonValue"
    }









    fun backSpaceAction(view: View)
    {
        val length= input.length()
        if(length>0)
            input.text= input.text.subSequence(0, length-1)

    }


    fun equalsAction(view: View)
    {
       output.text=calculateResults()
    }

    private fun calculateResults(): String
    {

        val digitsOperators= digitsOperators()
        if(digitsOperators.isEmpty()) return ""

        val timesDivision= timesDivisionCalculate(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result= addSubtractCalculate(timesDivision)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float
    {

        var result= passedList[0] as Float

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator= passedList[i]
                val nextDigit= passedList[i+1] as Float
                if(operator == '+')
                    result += nextDigit
                if(operator == '-')
                    result -= nextDigit
            }
        }
        return result

    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list= passedList
        while(list.contains('X') || list.contains('/'))
        {
            list= calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList= mutableListOf<Any>()
        var restartIndex= passedList.size

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex && i<restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i-1] as Float
                val nextDigit = passedList[i+1] as Float
                when(operator)
                {
                    'X' ->
                    {
                        newList.add(prevDigit*nextDigit)
                        restartIndex= i+1
                    }
                    '/' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex= i+1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }

            }

            if(i>restartIndex)
                newList.add(passedList[i])

        }

        return newList
    }

    private fun digitsOperators(): MutableList<Any>
    {
        val list= mutableListOf<Any>()
        var currentDigit=""
        for(character in input.text)
        {
            if(character.isDigit() || character=='.')
                currentDigit+=character
            else
            {
                list.add(currentDigit.toFloat())
                currentDigit=""
                list.add(character)
            }
        }

        if(currentDigit!="")
            list.add(currentDigit.toFloat())

        return list
    }



}










