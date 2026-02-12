package com.goble.studentlist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.goble.studentlist.viewmodel.StudentViewModel

@Composable
fun Homepage(modifier: Modifier = Modifier, viewModel: StudentViewModel) {
    val studentData = viewModel.studentData.observeAsState()
    val isLoading = viewModel.isLoading.observeAsState()
    var hasStudent by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                viewModel.getStudentData()
            }
        ) {
            Text(text = "Get Student Data")
        }

        if(isLoading.value == true) {
            CircularProgressIndicator()
            hasStudent = true
        } else {
            if (hasStudent) {
                StudentCard(studentData)
            }
        }
    }
}