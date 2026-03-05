package com.goble.myquizapp.ui.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goble.myquizapp.data.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    category: Category,
    onQuizFinished: () -> Unit,
    viewModel: QuizViewModel = viewModel()
) {
    val state  = viewModel.uiState

    // Initialize the quiz once when this screen is first shown
    LaunchedEffect(category) {
        viewModel.startQuiz(category)
    }

    // Trigger navigation when quiz finishes
    LaunchedEffect(state.isFinished) {
        if(state.isFinished) onQuizFinished()
    }

    val question = state.currentQuestion ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(category.displayName) },
                actions = {
                    Text(
                        text = "${state.currentIndex + 1} / ${state.totalQuestions}",
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Progress Bar
            LinearProgressIndicator(
                progress = { state.progress },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            // Question Text
            Text(
                text = question.question,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Answer Options
            question.options.forEachIndexed { index, option ->
                AnswerButton(
                    text = option,
                    state = when {
                        state.selectedOptionIndex == null -> AnswerState.IDLE
                        index == question.answer -> AnswerState.CORRECT
                        index == state.selectedOptionIndex -> AnswerState.WRONG
                        else -> AnswerState.IDLE
                    },
                    onClick = { viewModel.selectAnswer(index) },
                    enabled = state.selectedOptionIndex == null
                )
            }

            Spacer(Modifier.weight(1f))

            // Show 'Next' only after user has answered
            if (state.selectedOptionIndex != null) {
                Button(
                    onClick = viewModel::nextQuestion,
                    modifier = Modifier.fillMaxWidth()
                ){
                    val isLast = state.currentIndex == state.totalQuestions - 1
                    Text( if (isLast) "See Results" else "Next Questions")
                }
            }
        }
    }
}