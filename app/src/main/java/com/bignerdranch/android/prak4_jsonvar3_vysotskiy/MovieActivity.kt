package com.bignerdranch.android.prak4_jsonvar3_vysotskiy

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences
    private val gson = Gson()
    private var movies = ArrayList<Films>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        preferences = getSharedPreferences("movies_data", MODE_PRIVATE)

        val titleEditText = findViewById<EditText>(R.id.movieTitleEditText)
        val yearEditText = findViewById<EditText>(R.id.movieYearEditText)
        val genreEditText = findViewById<EditText>(R.id.movieGenreEditText)
        val budgetEditText = findViewById<EditText>(R.id.movieBudgetEditText)
        val addButton = findViewById<Button>(R.id.addMovieButton)
        val showButton = findViewById<Button>(R.id.showMoviesButton)
        val moviesTextView = findViewById<TextView>(R.id.moviesTextView)
        loadMovies()
        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val yeartext = yearEditText.text.toString()
            val genre = genreEditText.text.toString()
            val budgettext = budgetEditText.text.toString()

            if (title.isEmpty() || yeartext.isEmpty() || genre.isEmpty() || budgettext.isEmpty()) {
                Toast.makeText(
                    this,
                    "Заполните все поля",
                Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener

            }
            val budget = budgettext.toInt()
            val year = yeartext.toInt()
            val movie = Films(title, budget, year, genre)
            movies.add(movie)
            saveMovies()
            titleEditText.text.clear()
            yearEditText.text.clear()
            genreEditText.text.clear()
            budgetEditText.text.clear()
            Toast.makeText(
                this,
                "Фильм сохранён",
            Toast.LENGTH_SHORT
            ).show()
        }
        showButton.setOnClickListener {
            if (movies.isEmpty()) {
                moviesTextView.text = "Коллекция пуста"
            } else {
                var result = ""
                for (i in movies.indices) {
                    result += "${i + 1}. ${movies[i].title}\n"
                    result += "Год: ${movies[i].year}\n"
                    result += "Жанр: ${movies[i].genre}\n"
                    result += "Бюджет: ${movies[i].budget}\n"
                }
                moviesTextView.text = result
            }
        }
    }
    private fun saveMovies() {
        val json = gson.toJson(movies)
        preferences.edit()
            .putString("movies", json)
        .apply()
    }
    private fun loadMovies() {
        val json = preferences.getString("movies", null)
        if (json != null) {
            val type = object : TypeToken<ArrayList<Films>>() {}.type
            movies = gson.fromJson(json, type)
        }
    }
}