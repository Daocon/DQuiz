package com.coda.dquiz.presentation.screens.quiz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coda.dquiz.data.database.QuizDatabase
import com.coda.dquiz.data.model.Answer
import com.coda.dquiz.data.model.Question
import com.coda.dquiz.data.model.Quiz
import kotlinx.coroutines.launch


class QuizViewModel(private val database: QuizDatabase) : ViewModel() {

    private val quizDao = database.quizDao()

    fun insertQuiz(quiz: Quiz, onQuizInserted: (Long) -> Unit) {
        viewModelScope.launch {
            val quizId = database.quizDao().insertQuiz(quiz)
            Log.d("QuizViewModel", "Quiz inserted with ID: $quizId")
            onQuizInserted(quizId)
        }
    }

    fun insertQuestion(question: Question) {
        viewModelScope.launch {
            val questionId = quizDao.insertQuestion(question)
            Log.d("QuizViewModel", "Question inserted with ID: $questionId")
        }
    }

    fun insertAnswer(answer: Answer) {
        viewModelScope.launch {
            val answerId = quizDao.insertAnswer(answer)
            Log.d("QuizViewModel", "Answer inserted with ID: $answerId")
        }
    }

    fun getAllQuizzes() = quizDao.getAllQuizzes()

    fun getQuestionsForQuiz(quizId: Long): LiveData<List<Question>> {
        return quizDao.getQuestionsForQuiz(quizId)
    }

    fun getAnswersForQuestion(questionId: Long): LiveData<List<Answer>> {
        return quizDao.getAnswersForQuestion(questionId)
    }
}

class QuizViewModelFactory(private val database: QuizDatabase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}