package com.goble.userinputpractice.ui.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleInputExample() {
    // 1. Create a state variable to hold the text
    var name by remember { mutableStateOf("") }

    // 2. Layout a column so our widgets stack vertically
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // 3. The TextField composable
        TextField(
            value = name,                   // current text from state
            onValueChange = { newText ->
                name = newText              // update the state on every keystroke
            },
            label = {
                Text("Enter your name")
            },
            placeholder = {
                Text("e.g. John E. Jones")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Hello ${name}!")
    }
}