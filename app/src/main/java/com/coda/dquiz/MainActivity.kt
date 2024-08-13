package com.coda.dquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.coda.dquiz.data.database.QuizDatabase
import com.coda.dquiz.navigation.QuizNavGraph

import com.coda.dquiz.presentation.screens.quiz.QuizViewModel
import com.coda.dquiz.presentation.screens.quiz.QuizViewModelFactory
import com.coda.dquiz.ui.theme.DQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Get an instance of QuizDatabase
            val database = QuizDatabase.getDatabase(applicationContext)

            // Create an instance of QuizViewModel using the factory
            val quizViewModel = ViewModelProvider(this, QuizViewModelFactory(database))
                .get(QuizViewModel::class.java)

            // Create a NavHostController
            val navController = rememberNavController()

            setContent {
                DQuizTheme {
                    QuizNavGraph(navController, quizViewModel)
                }
            }
        }
    }
}