package com.goble.myquizapp.ui.results

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.goble.myquizapp.data.Question
import com.goble.myquizapp.ui.navigation.Screen
import com.goble.myquizapp.ui.question.QuizViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    onPlayAgain: () -> Unit,
    viewModel: QuizViewModel = QuizViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Results") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Score Summary
            item {
                ScoreSummaryCard(score = state.score, total = state.totalQuestions)
            }

            // Per-question breakdown
            itemsIndexed(state.questions) { index, question ->
                val wasCorrect = state.answers.getOrElse(index) { false }
                QuestionResultRow(
                    question = question,
                    wasCorrect = wasCorrect
                )
            }

            item {
                Button(
                    onClick = {
                        viewModel.resetQuiz()
                        onPlayAgain()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Play Again")
                }
            }
        }
    }
}


@Composable
fun ScoreSummaryCard(score:Int, total:Int) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "$score / $total", style = MaterialTheme.typography.displayMedium)
            Text(
                text = when {
                    score == total -> "Perfect score!"
                    score >= total * .7 -> "Great job!"
                    score >= total * .4 -> "Keep Practicing!"
                    else -> "Better luck next time..."
                },
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun QuestionResultRow(question: Question, wasCorrect: Boolean) {
    val containerColor = when(wasCorrect) {
        true -> Color(0xFF2E7D32)
        else -> Color(0xFFC62828)
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Text(question.question)
    }
}