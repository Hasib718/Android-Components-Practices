@file:OptIn(ExperimentalMaterial3Api::class)

package com.hasib.basiccompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hasib.basiccompose.ui.theme.BasicComposeTheme

object ListScreen {
    const val ROUTE_NAME = "list_screen"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Basic Android",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null

                            )
                        }
                    }
                )
            }) {
            val checkboxStates = remember {
                mutableStateListOf<Boolean>()
            }
            val listItems = ((0..100).map {
                checkboxStates.add(false)
                it.toString()
            }).toList()

            LazyColumn {
                items(listItems.size) { index ->
                    androidx.compose.material3.ListItem(
                        headlineText = {
                            Text(text = "List Item - ${listItems[index]}")
                        },
                        leadingContent = {
                            Icon(imageVector = Icons.Default.Star, contentDescription = "Star")
                        },
                        trailingContent = {
                            Checkbox(
                                checked = checkboxStates[index],
                                onCheckedChange = { checkboxStates[index] = it })
                        })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    BasicComposeTheme {
        ListScreen(navController = rememberNavController())
    }
}