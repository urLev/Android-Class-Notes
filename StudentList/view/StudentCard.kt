package com.goble.studentlist.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goble.studentlist.UIText
import com.goble.studentlist.data.Student
import com.goble.studentlist.R

@Composable
fun StudentCard(student: State<Student?>) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        CardContent(student)
    }
}

@Composable
fun CardContent(student: State<Student?>) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(12.dp)
            .animateContentSize(animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ))
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .padding(12.dp),
        ) {
            Text(text = "${student.value?.name}",
                style = MaterialTheme.typography.headlineLarge)
            Text(text = "${student.value?.pronouns}",
                style = MaterialTheme.typography.displaySmall)

            if (expanded) {
                Text(text = "${student.value?.transcript}")
            }
        }

        val descriptionString:String = UIText.StringResource(
            resId = if (expanded) R.string.show_less else R.string.show_more
        ).toString()

        IconButton(
            onClick = {
                expanded = !expanded
            }
        ) {
            Icon(imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = descriptionString,
            )
        }
    }
}