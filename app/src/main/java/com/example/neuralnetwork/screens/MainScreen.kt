package com.example.neuralnetwork.screens

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neuralnetwork.R
import com.example.neuralnetwork.models.MainScreenViewModel
import com.example.neuralnetwork.navigation.Routes
import com.example.neuralnetwork.ui.theme.BabyPink
import com.example.neuralnetwork.ui.theme.centerColor
import com.example.neuralnetwork.ui.theme.edgeColor
import com.example.neuralnetwork.ui_Components.MainNavBar
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
            LocalContext.current.applicationContext as Application
        )
    ),
    onNavigationToCreate: () -> Unit,
    onNavigateToMyNetworks: () -> Unit,
    onNavigateToTry: (Routes.Try) -> Unit,
    onNavigateToInspect: (Routes.Inspect) -> Unit,
    onNavigationToSettings: () -> Unit
){
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.checkLogged()
        Log.d("LOGGED", FirebaseAuth.getInstance().currentUser.toString())
        Log.d("LOGGED",viewModel.isLogged.toString())
    }
    Scaffold(
            containerColor = Color.White,
            modifier = Modifier.fillMaxSize(),
             bottomBar = { MainNavBar(viewModel.MainNavItems , onPressed = {index ->
                 when(index){
                     0 -> {
                         onNavigateToTry(
                             Routes.Try()
                         )
                     }
                     1 -> {
                         onNavigateToInspect(
                             Routes.Inspect()
                         )
                     }
                     2 -> {
                         onNavigationToSettings()
                     }
                 }
             }) },
             topBar = {
               com.example.neuralnetwork.ui_Components.TopAppBar("Neural Network Playground")
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.weight(2f)
                            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 2.5.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(centerColor, edgeColor)
                                )
                            ).clickable(
                                onClick = {
                                    if (viewModel.isLogged){
                                        onNavigationToCreate()
                                    }
                                    else{
                                        Toast.makeText(context,"Sign in first to access this functionality",
                                            Toast.LENGTH_SHORT).show()
                                    }}
                            )
                    )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.traintry_brainbgr),
                            contentDescription = "train create image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = "Create & Train ",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    Card(
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.weight(2f)
                            .padding(start = 2.5.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(centerColor, edgeColor)
                                )
                            )
                            .clickable(
                                onClick =
                                {
                                    if (viewModel.isLogged){
                                    onNavigateToMyNetworks()
                                    }
                                    else{
                                    Toast.makeText(context,"Sign in first to access this functionality",
                                        Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                    )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.network_rackbgr),
                            contentDescription = "Saved Networks Icon",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = "My Networks",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic
                        )
                    }

                }
                Text(
                    text = "OPTIONS",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp, top = 0.dp, bottom = 5.dp, end = 10.dp).fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal)

                Column(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 5.dp, bottom = 10.dp, end = 10.dp)) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = edgeColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(
                                onClick = {
                                    if (viewModel.isLogged){
                                        onNavigateToTry(
                                            Routes.Try()
                                        )                                    }
                                    else{
                                        Toast.makeText(context,"Sign in first to access this functionality",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.flask_samplebgr),
                                contentDescription = "Sample network logo",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.height(100.dp))
                            Text(
                                text = "Try Networks",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                modifier = Modifier.padding(20.dp),
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Italic
                            )
                        }

                    }
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = edgeColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 5.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(
                                onClick = {
                                    if (viewModel.isLogged){
                                        onNavigateToInspect(Routes.Inspect())
                                    }
                                    else{
                                        Toast.makeText(context,"Sign in first to access this functionality",
                                            Toast.LENGTH_SHORT).show()
                                    }
                            })
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.visualize_tiltedsquaresbgr),
                                contentDescription = "Inspect logo",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.height(100.dp)
                            )
                            Text(
                                text = "Inspect",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                modifier = Modifier.padding(20.dp),
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = edgeColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 5.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(
                                onClick = {
                                onNavigationToSettings()
                            })
                            .background(
                                brush = Brush.radialGradient(listOf(edgeColor,edgeColor))
                            )
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.setting_bgr),
                                contentDescription = "settings logo",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.height(100.dp)
                            )
                            Text(
                                text = "Settings",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                modifier = Modifier.padding(20.dp),
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }


                }

            }

        }

}