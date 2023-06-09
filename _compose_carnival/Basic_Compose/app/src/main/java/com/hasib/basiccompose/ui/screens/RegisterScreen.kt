@file:OptIn(ExperimentalMaterial3Api::class)

package com.hasib.basiccompose.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hasib.basiccompose.model.User
import com.hasib.basiccompose.ui.theme.BasicComposeTheme

object RegisterScreen {
    const val ROUTE_NAME = "register_screen"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(navController: NavController) {
    val database = Firebase.database.reference
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Register Screen",
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
                            onClick = { navController.navigateUp() },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null

                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val name = remember {
                    mutableStateOf("")
                }
                val nameError = remember {
                    mutableStateOf(false)
                }
                val email = remember {
                    mutableStateOf("")
                }
                val emailError = remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    value = name.value,
                    onValueChange = {
                        name.value = it
                        if (nameError.value && it.isNotEmpty()) {
                            nameError.value = false
                        }
                    },
                    maxLines = 1,
                    label = { Text(text = "Enter name") },
                    singleLine = true,
                    shape = CircleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    isError = nameError.value,
                    supportingText = {
                        if (nameError.value) {
                            Text(text = "Name cannot be empty")
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                        if (isEmailValid(it)) {
                            emailError.value = ""
                        } else {
                            emailError.value = "Enter valid email"
                        }
                    },
                    shape = CircleShape,
                    maxLines = 1,
                    label = { Text(text = "Enter email") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    isError = emailError.value.isNotEmpty(),
                    supportingText = {
                        if (emailError.value.isNotEmpty()) {
                            Text(text = emailError.value)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    )
                )

                Button(
                    onClick = {
                        if (name.value.isEmpty()) {
                            nameError.value = true
                            return@Button
                        }
                        if (email.value.isEmpty()) {
                            emailError.value = "Email cannot be empty"
                            return@Button
                        }

                        database.push().setValue(User(name.value, email.value))
                            .addOnCompleteListener {
                                sharedPreferences.edit().putBoolean("registered", true).apply()
                                Toast.makeText(context, "Recorded Added", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                                navController.navigate(ListScreen.ROUTE_NAME)
                            }.addOnFailureListener {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            }
                    },
                    modifier = Modifier
                        .width(300.dp)
                        .height(75.dp)
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(text = "Register Here!", fontSize = 24.sp)
                }
            }
        }
    }
}

private fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+\$")
    return email.matches(emailRegex)
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    BasicComposeTheme {
        RegisterScreen(navController = rememberNavController())
    }
}