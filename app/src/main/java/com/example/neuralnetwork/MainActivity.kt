package com.example.neuralnetwork

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.neuralnetwork.ai.datasets
import com.example.neuralnetwork.firebase.FirebaseAuthRepo
import com.example.neuralnetwork.navigation.NavGraph
import com.example.neuralnetwork.screens.TryScreen
import com.example.neuralnetwork.ui.theme.NeuralNetworkTheme
import com.example.neuralnetwork.ui_Components.MyNetworkItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuthRepo()
        val currentUser = auth.currentUser()
        enableEdgeToEdge()
        setContent {
            NeuralNetworkTheme {
                NavGraph(currentUser = currentUser)
            }
        }
    }
}
