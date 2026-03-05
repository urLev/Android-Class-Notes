package com.goble.myquizapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goble.myquizapp.data.Category
import com.goble.myquizapp.data.QuestionRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onStartQuiz: (Category)->Unit ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Quiz Game")
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose a Category",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier. height(8.dp))

            QuestionRepository.getCategories().forEach { category ->
                CategoryCard(
                    category = category,
                    onClick = { onStartQuiz(category) },
                )
            }
        }
    }
}

@Composable
fun CategoryCard(category: Category, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Text(
            text = category.displayName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(24.dp)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun previewHome() {
//    HomeScreen()
//}