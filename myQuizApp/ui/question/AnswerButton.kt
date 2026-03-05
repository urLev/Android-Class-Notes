package com.goble.myquizapp.ui.question

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class AnswerState { IDLE, CORRECT, WRONG }

@Composable
fun AnswerButton(
    text: String,
    state: AnswerState,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val containerColor = when (state) {
        AnswerState.IDLE -> MaterialTheme.colorScheme.surfaceVariant
        AnswerState.CORRECT -> Color(0xFF2E7D32)
        AnswerState.WRONG -> Color(0xFFC62828)
    }

    val contentColor = when (state) {
        AnswerState.IDLE -> MaterialTheme.colorScheme.onSurfaceVariant
        else -> Color.White
    }

    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text, modifier = Modifier.padding(vertical = 4.dp))
    }
}