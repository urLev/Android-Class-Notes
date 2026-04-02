package com.example.apipractice.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apipractice.ui.viewModels.PlayerUiState
import com.example.apipractice.ui.viewModels.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerSearchScreen(
    viewModel: PlayerViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("MLB Player Lookup")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    viewModel.onSearchQueryChange(it)
                },
                label = {
                    Text("Search Player (e.g. Wheeler) ")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = viewModel::searchPlayers,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search Player")
            }

            Spacer(Modifier.height(16.dp))

            when(val state = uiState) {
                is PlayerUiState.Idle -> EmptyState("Enter a name above and tap search")
                is PlayerUiState.Loading -> LoadingScreen()
                is PlayerUiState.Error -> ErrorMessage(state.message)
                is PlayerUiState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.players) { player ->
                            PlayerCard(player = player)
                        }
                    }
                }
            }
        }
    }
}