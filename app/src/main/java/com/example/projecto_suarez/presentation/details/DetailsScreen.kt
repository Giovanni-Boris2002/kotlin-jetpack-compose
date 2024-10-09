package com.example.projecto_suarez.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projecto_suarez.data.remote.dto.LabResponse
import com.example.projecto_suarez.presentation.details.components.DetailsTopBar

@Composable
fun DetailsScreen(
    event: (DetailsEvent) -> Unit,
    lab: LabResponse,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit)
{
    Column(
        modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DetailsTopBar(
            onBackClick = navigateUp
        )

        Text(text = "Lab: ${lab.name}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            event(DetailsEvent.enrollStudent("1",lab.id, navigateToHome))

        }) {
            Text(text = "Enroll in Lab")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            event(DetailsEvent.deregisterStudent("1",lab.id, navigateToHome))
        }) {
            Text(text = "Unregister from Lab")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Students Enrolled:")
        LazyColumn {
            items(lab.students) { student ->
                Text(text = student.name, modifier = Modifier.padding(4.dp))
            }
        }
    }
}