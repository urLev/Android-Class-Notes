package com.goble.userinputpractice.ui.input

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun ValidatedEmailField() {
    var email by remember { mutableStateOf("") }
    var touched by remember {mutableStateOf(false)}
    var isError = touched && !email.contains("@")

    OutlinedTextField(
        value = email,
        onValueChange = {
            email = it
            touched = true          // now validation will wait until the user starts typing
        },
        label = { Text("Email Address")},
        isError = isError,
        // supportingText shows up below the field
        supportingText = {
            if (isError) {
                Text(
                    text = "Please enter a valid email",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}