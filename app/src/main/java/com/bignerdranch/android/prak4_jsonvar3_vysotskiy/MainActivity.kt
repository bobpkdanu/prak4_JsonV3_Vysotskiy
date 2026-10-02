package com.bignerdranch.android.prak4_jsonvar3_vysotskiy

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.*

class MainActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = getSharedPreferences("user_data", MODE_PRIVATE)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val name = nameEditText.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val json = Gson().toJson(name)

            preferences.edit()
                .putString("user", json)
                .apply()

            val intent = Intent(this, MovieActivity::class.java)
            startActivity(intent)
        }
    }
}