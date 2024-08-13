package com.coda.dquiz.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class Question(
    @PrimaryKey(autoGenerate = true) val questionId: Long = 0,
    val quizId: Long,
    val questionText: String,
    val correctAnswer: String
)