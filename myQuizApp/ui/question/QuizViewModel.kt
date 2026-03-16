package com.goble.myquizapp.ui.question

import androidx.lifecycle.ViewModel
import com.goble.myquizapp.data.Category
import com.goble.myquizapp.data.Question
import com.goble.myquizapp.data.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
    private val _uiState = MutableStateFlow(QuizUIState())
    val uiState: StateFlow<QuizUIState> = _uiState.asStateFlow()
//    var uiState by mutableStateFlow(QuizUIState())

    fun startQuiz(category: Category) {
        if (uiState.value.questions.isNotEmpty()) return
        val questions = QuestionRepository.getQuestions(category)
//        uiState = QuizUIState(questions = questions)
        _uiState.update { currentState ->
            currentState.copy(questions = questions)
        }
    }

    fun selectAnswer(optionIndex:Int) {
        if(uiState.value.selectedOptionIndex != null) return
//        uiState = uiState.value.copy(selectedOptionIndex = optionIndex)
        _uiState.update { currentState ->
            currentState.copy(selectedOptionIndex = optionIndex)
        }
    }

    fun nextQuestion() {
        val current = uiState.value.currentQuestion ?: return
        val isCorrect = uiState.value.selectedOptionIndex == current.answer
        val newAnswers = uiState.value.answers + isCorrect
        val nextIndex = uiState.value.currentIndex + 1
        val finished = nextIndex >= uiState.value.totalQuestions

//        uiState = uiState.copy(
//            currentIndex = nextIndex,
//            selectedOptionIndex = null,
//            answers = newAnswers,
//            isFinished = finished
//        )
        _uiState.update { currentState ->
            currentState.copy(
                currentIndex = nextIndex,
                selectedOptionIndex = null,
                answers = newAnswers,
                isFinished = finished
            )
        }
    }

    fun resetQuiz() {
//        uiState = QuizUIState()
        _uiState.value = QuizUIState()

    }

    override fun onCleared() {
        super.onCleared()
        print("viewModel cleared")
    }
}