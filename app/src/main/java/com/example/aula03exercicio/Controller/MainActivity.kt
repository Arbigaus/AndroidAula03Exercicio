package com.example.aula03exercicio.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aula03exercicio.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

    }
}