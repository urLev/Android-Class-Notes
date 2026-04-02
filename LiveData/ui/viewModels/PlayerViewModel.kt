package com.example.apipractice.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apipractice.data.Player
import com.example.apipractice.data.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class PlayerUiState {
    object Idle: PlayerUiState()
    object Loading : PlayerUiState()
    data class Success(val players: List<Player>) : PlayerUiState()
    data class Error(val message: String) : PlayerUiState()
}

class PlayerViewModel(
    private val repository: PlayerRepository = PlayerRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<PlayerUiState>(PlayerUiState.Idle)
    val uiState: StateFlow<PlayerUiState> = _uiState

    // Keeps track of what the user has typed
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchPlayers() {
        val query = _searchQuery.value.trim()
        if (query.isEmpty()) {
            _uiState.value = PlayerUiState.Idle
            return
        }

        viewModelScope.launch {
            _uiState.value = PlayerUiState.Loading
            try {
                val players = repository.searchAllPlayers(query)
                _uiState.value = if (players.isEmpty()) {
                    PlayerUiState.Error("No players found for \"$query\"")
                } else {
                    PlayerUiState.Success(players)
                }
            } catch (e: Exception) {
                _uiState.value = PlayerUiState.Error(
                    "Network error: ${e.localizedMessage ?: "Unknown error"}"
                )
            }
        }
    }
}