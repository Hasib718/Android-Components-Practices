package com.hasib.composelayouts

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hasib.composelayouts.ui.theme.ComposeLayoutsTheme
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLayoutsTheme {
                BodyContentStaggered()
            }
        }
    }
}

@Composable
fun LayoutsCodelab() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val top = remember { mutableStateOf(false) }
    val bottom = remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text(text = "Drawer content")
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.AddChart, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape) {
                IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "....")
                }
            }
        },
        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ExtendedFloatingActionButton(
                    text = { Text(text = "Scroll to the top") },
                    onClick = { top.value = true })
                ExtendedFloatingActionButton(
                    text = { Text(text = "Scroll to the bottom") },
                    onClick = { bottom.value = true })

            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = false,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            BodyContent()
            Spacer(modifier = Modifier.padding(top = 10.dp))
            LazyScrollingList(top, bottom)
        }
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    MyOwnColumn(modifier.padding(8.dp)) {
        Text("MyOwnColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }
}

@Preview
@Composable
fun LayoutsCodelabPreview() {
    ComposeLayoutsTheme {
        BodyContent()
    }
}