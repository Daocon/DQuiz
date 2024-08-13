package com.coda.dquiz.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answer_table")
data class Answer(
    @PrimaryKey(autoGenerate = true) val answerId: Long = 0,
    val questionId: Long,
    val answerText: String,
    val isCorrect: Boolean
)