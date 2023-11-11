package com.example.calc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       val nameet=binding.editText
        binding.button.setOnClickListener {
            binding.editText.getText().toString()
            val myIntent = Intent(this, calc_page::class.java)
            val name = nameet.text.toString()
            myIntent.putExtra("Name", name)




            startActivity(myIntent)

        }




    }
}