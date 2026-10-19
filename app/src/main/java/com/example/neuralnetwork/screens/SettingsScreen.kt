package com.example.neuralnetwork.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neuralnetwork.R
import com.example.neuralnetwork.models.SettingScreenViewModel
import com.example.neuralnetwork.operationState.OperationState
import com.example.neuralnetwork.ui.theme.edgeColor
import com.example.neuralnetwork.ui.theme.offWhite
import com.example.neuralnetwork.ui_Components.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingScreenViewModel = viewModel(),
    onNavigateToRegister: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val signOutState = viewModel.signOutState
    val context = LocalContext.current

    LaunchedEffect(signOutState) {
        when(signOutState){
            is OperationState.Success ->{
                Toast.makeText(context,signOutState.data, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Scaffold(
        containerColor = Color.White,
        contentColor = Color.Black,
        topBar = {
            TopAppBar("Settings")
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Account",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = offWhite
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(R.drawable.account_circle_24px),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.Black
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Signed in as",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )

                        Text(
                            text = viewModel.emailState.collectAsState().value,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = edgeColor,
                    contentColor = Color.White
                )
            ) {

                Icon(
                    painter = painterResource(R.drawable.logout_24px),
                    contentDescription = "logout"
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text("Sign Out", modifier = Modifier.padding(10.dp))
            }
            Button(
                enabled = !viewModel.isLogged.collectAsState().value,
                onClick = {
                    onNavigateToRegister()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = edgeColor,
                    contentColor = Color.White
                )
            ) {

                Icon(
                    painter = painterResource(R.drawable.email),
                    contentDescription = "sign in"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Sign In", modifier = Modifier.padding(10.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            HorizontalDivider()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Neural Network",
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = "Version 1.0.0",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Developed by MD Talib",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

    if (showDialog) {

        AlertDialog(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            textContentColor = Color.Black,
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text("Sign Out")
            },
            text = {
                Text("Are you sure you want to sign out?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        viewModel.signOut()
                    }
                ) {
                    Text("Sign Out", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Cancel", color = Color.Black)
                }
            }
        )
    }
}