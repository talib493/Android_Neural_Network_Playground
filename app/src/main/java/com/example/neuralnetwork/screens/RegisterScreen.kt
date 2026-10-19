package com.example.neuralnetwork.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neuralnetwork.R
import com.example.neuralnetwork.models.RegisterScreenViewModel
import com.example.neuralnetwork.operationState.OperationState
import com.example.neuralnetwork.ui.theme.edgeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterScreenViewModel = viewModel(),
    onNavigateToMain: () -> Unit
){
    val context = LocalContext.current
    val loginState = viewModel.LoginState
    val registerState = viewModel.registerState
    LaunchedEffect(loginState) {
        when (loginState) {
            is OperationState.Success -> {
                Toast.makeText(context, loginState.data, Toast.LENGTH_SHORT).show()
                viewModel.resetLoginState()
                onNavigateToMain()
            }
            is OperationState.Error -> {
                Toast.makeText(context, loginState.message, Toast.LENGTH_SHORT).show()
                viewModel.resetLoginState()
            }
            else -> {}
        }
    }
    LaunchedEffect(registerState) {
        when (registerState) {
            is OperationState.Success -> {
                Toast.makeText(context, registerState.data, Toast.LENGTH_SHORT).show()
                viewModel.resetRegisterState()
                onNavigateToMain()
            }
            is OperationState.Error -> {
                Toast.makeText(context, registerState.message, Toast.LENGTH_SHORT).show()
                viewModel.resetRegisterState()
            }
            else -> {}
        }
    }
    val isLoading = loginState is OperationState.Loading || registerState is OperationState.Loading
    Scaffold(
        containerColor = Color.White,
        topBar = {
            com.example.neuralnetwork.ui_Components.TopAppBar("Register & Login")
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(20.dp)) {
            Card(
                modifier = Modifier.padding(top = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults
                    .cardElevation(8.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.resgister_logo),
                        contentDescription = "Register Screen Logo",
                        modifier = Modifier.size(110.dp)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    OutlinedTextField(
                        enabled = !isLoading,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = edgeColor,
                            unfocusedBorderColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = edgeColor,
                            focusedLabelColor = edgeColor,
                            unfocusedLabelColor = edgeColor,
                            disabledBorderColor = Color.Gray,
                            disabledTextColor = Color.Gray,
                            disabledLabelColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        value = viewModel.username,
                        onValueChange = { viewModel.UpdateUsername(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "E-mail") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.email),
                                contentDescription = "Email Icon",
                                tint = Color.Black
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(2.5.dp))

                    OutlinedTextField(
                        enabled = !isLoading,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = edgeColor,
                            unfocusedBorderColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = edgeColor,
                            focusedLabelColor = edgeColor,
                            unfocusedLabelColor = edgeColor,
                            disabledBorderColor = Color.Gray,
                            disabledTextColor = Color.Gray,
                            disabledLabelColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        value = viewModel.password,
                        onValueChange = { viewModel.UpdatePassword(it) },
                        visualTransformation = if (!viewModel.passVisibility) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Password") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.password_24px),
                                contentDescription = "Password Icon",
                                tint = Color.Black
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.passVisibility = !viewModel.passVisibility
                            }) {
                                Icon(
                                    painter = painterResource(
                                        if (!viewModel.passVisibility) R.drawable.visibility_24px else R.drawable.visibility_off_24px
                                    ),
                                    contentDescription = "Password Visibility",
                                    tint = Color.Black
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = edgeColor,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = edgeColor,
                                contentColor = Color.White
                            ),
                            onClick = {
                                if (viewModel.isInternetAvailable(context)) {
                                    viewModel.register()
                                }else{
                                    Toast.makeText(context,"No Internet Connection", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "REGISTER",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = edgeColor,
                                contentColor = Color.White
                            ),
                            onClick = {
                                if (viewModel.isInternetAvailable(context)) {
                                    viewModel.login()
                                }else{
                                    Toast.makeText(context,"No Internet Connection", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "LOG IN",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }


            }

        }
    }


}