package com.example.quizapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.coda.dquiz.data.model.Answer
import com.coda.dquiz.data.model.Question
import com.coda.dquiz.data.model.Quiz
import com.coda.dquiz.presentation.screens.quiz.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizCreationScreen(viewModel: QuizViewModel, navController: NavHostController) {
    var quizTitle by remember { mutableStateOf("") }
    var questionText by remember { mutableStateOf("") }
    var correctAnswer by remember { mutableStateOf("") }
    var incorrectAnswers by remember { mutableStateOf(listOf("", "", "")) }
    val questionList = remember { mutableStateListOf<Question>() }
    val answerList = remember { mutableStateListOf<Answer>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Quiz") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(0xFF1976D2)
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE3F2FD)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = quizTitle,
                            onValueChange = { quizTitle = it },
                            label = { Text("Quiz Title") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color(0xFFFFFFFF),
                                focusedLabelColor = Color(0xFF1976D2),
                                unfocusedLabelColor = Color(0xFF90CAF9)
                            )
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE3F2FD)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = questionText,
                            onValueChange = { questionText = it },
                            label = { Text("Question") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color(0xFFFFFFFF),
                                focusedLabelColor = Color(0xFF1976D2),
                                unfocusedLabelColor = Color(0xFF90CAF9)
                            )
                        )

                        OutlinedTextField(
                            value = correctAnswer,
                            onValueChange = { correctAnswer = it },
                            label = { Text("Correct Answer") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color(0xFFFFFFFF),
                                focusedLabelColor = Color(0xFF1976D2),
                                unfocusedLabelColor = Color(0xFF90CAF9)
                            )
                        )

                        incorrectAnswers.forEachIndexed { index, answer ->
                            OutlinedTextField(
                                value = answer,
                                onValueChange = { newAnswer ->
                                    incorrectAnswers =
                                        incorrectAnswers.toMutableList().apply { this[index] = newAnswer }
                                },
                                label = { Text("Incorrect Answer ${index + 1}") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color(0xFFFFFFFF),
                                    focusedLabelColor = Color(0xFF1976D2),
                                    unfocusedLabelColor = Color(0xFF90CAF9)
                                )
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        val question = Question(
                            quizId = 0,
                            questionText = questionText,
                            correctAnswer = correctAnswer
                        )
                        questionList.add(question)

                        incorrectAnswers.forEach { answerText ->
                            val isCorrect = answerText == correctAnswer
                            val answer = Answer(
                                questionId = 0,
                                answerText = answerText,
                                isCorrect = isCorrect
                            )
                            answerList.add(answer)
                        }

                        questionText = ""
                        correctAnswer = ""
                        incorrectAnswers = listOf("", "", "")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2)
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Question", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Question", color = Color.White)
                }

                Button(
                    onClick = {
                        val quiz = Quiz(title = quizTitle)
                        viewModel.insertQuiz(quiz) { quizId ->
                            questionList.forEach { question ->
                                val updatedQuestion = question.copy(quizId = quizId)
                                viewModel.insertQuestion(updatedQuestion)
                                answerList.filter { it.answerText == question.correctAnswer }
                                    .forEach { answer ->
                                        viewModel.insertAnswer(answer.copy(questionId = updatedQuestion.questionId))
                                    }
                                answerList.filter { it.answerText != question.correctAnswer }
                                    .forEach { answer ->
                                        viewModel.insertAnswer(answer.copy(questionId = updatedQuestion.questionId))
                                    }
                            }
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2)
                    )
                ) {
                    Icon(Icons.Default.Save, contentDescription = "Save Quiz", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save Quiz", color = Color.White)
                }
            }
        }
    )
}