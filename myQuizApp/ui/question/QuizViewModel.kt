package com.goble.myquizapp.ui.question

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.goble.myquizapp.data.Category
import com.goble.myquizapp.data.Question
import com.goble.myquizapp.data.QuestionRepository

data class QuizUIState(
    val questions: List<Question> = emptyList(),
    val currentIndex:Int = 0,
    val selectedOptionIndex:Int? = null,
    val answers:List<Boolean> = emptyList(),
    val isFinished: Boolean = false
) {
    val currentQuestion:Question? get() = questions.getOrNull(currentIndex)
    val totalQuestions:Int get() = questions.size
    val score:Int get() = answers.count { it }
    val progress: Float get() = if (totalQuestions == 0) 0f else currentIndex.toFloat() / totalQuestions
}

class QuizViewModel: ViewModel() {
    var uiState by mutableStateOf(QuizUIState())

    fun startQuiz(category: Category) {
        if (uiState.questions.isNotEmpty()) return
        val questions = QuestionRepository.getQuestions(category)
        uiState = QuizUIState(questions = questions)
    }

    fun selectAnswer(optionIndex:Int) {
        if(uiState.selectedOptionIndex != null) return
        uiState = uiState.copy(selectedOptionIndex = optionIndex)
    }

    fun nextQuestion() {
        val current = uiState.currentQuestion ?: return
        val isCorrect = uiState.selectedOptionIndex == current.answer
        val newAnswers = uiState.answers + isCorrect
        val nextIndex = uiState.currentIndex + 1
        val finished = nextIndex >= uiState.totalQuestions

        uiState = uiState.copy(
            currentIndex = nextIndex,
            selectedOptionIndex = null,
            answers = newAnswers,
            isFinished = finished
        )
    }

    fun resetQuiz() {
        uiState = QuizUIState()
    }
}