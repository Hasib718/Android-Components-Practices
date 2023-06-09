package com.hasib.basiccompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hasib.basiccompose.ui.theme.BasicComposeTheme

object NumberPadScreen {
    const val ROUTE_NAME = "number_pad"
}

@Composable
fun NumberPadScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        val list = listOf(
            listOf("C", "( )", "%", "X"),
            listOf("7", "8", "9", "÷"),
            listOf("4", "5", "6", "×"),
            listOf("1", "2", "3", "+"),
            listOf(".", "0", "=", "-")
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 0 until 5) {
                NumberRow(args = list[i], navController = navController)
                if (i != 4) {
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}

@Composable
fun NumberRow(args: List<String>, navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until 4) {
            Button(
                onClick = { navController.navigateUp() },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier
                    .size(100.dp)
                    .weight(2f)
            ) {
                val signsSize = if (args[i] in listOf("÷", "×", "+", "-")) 32.sp else 24.sp
                Text(text = args[i], fontSize = signsSize)
            }
            if (i != 3) {
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NumberPadScreenPreview() {
    BasicComposeTheme {
        NumberPadScreen(navController = rememberNavController())
    }
}