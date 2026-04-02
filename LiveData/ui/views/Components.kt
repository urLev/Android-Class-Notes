package com.example.apipractice.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.apipractice.data.Player
import com.example.apipractice.ui.viewModels.PlayerUiState
import com.example.apipractice.ui.viewModels.PlayerViewModel

@Composable
fun EmptyState(message:String) {
    Box(
        Modifier.fillMaxWidth()
                .padding(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ErrorMessage(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
       Text(
           text = "Error: $message",
           modifier = Modifier.padding(8.dp),
           color = MaterialTheme.colorScheme.onErrorContainer
       )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun PlayerCard(player: Player) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = player.fullName ?: "Unknown Player",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            player.team?.displayName?.let {
                Text(text = it, style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.height(8.dp))
            PlayerInfoRow("Position", player.position)
            PlayerInfoRow("Jersey", player.jersey?.let { "#$it" })
            PlayerInfoRow("Height / Weight",
                listOfNotNull(player.height, player.weight).joinToString(" · ")
                    .ifEmpty { null })
            PlayerInfoRow("Age", player.age?.toString())
            PlayerInfoRow("Bats / Throws", player.batsThrows)
            PlayerInfoRow("Debut Year", player.debutYear?.toString())
            PlayerInfoRow("Birthplace", player.birthPlace)
            if (!player.draft.isNullOrBlank()) {
                PlayerInfoRow("Draft", player.draft)
            }
            val status = if (player.active == true) "Active" else "Inactive"
            PlayerInfoRow("Status", status)
        }
    }
}

@Composable
fun PlayerInfoRow(label: String, value: String?) {
    if (value.isNullOrBlank()) return
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(120.dp)
        )
        Text(text = value, style = MaterialTheme.typography.bodySmall)
    }
}
