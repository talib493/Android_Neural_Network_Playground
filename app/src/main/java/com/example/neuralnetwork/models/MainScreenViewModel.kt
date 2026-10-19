package com.example.neuralnetwork.models

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.neuralnetwork.R
import com.example.neuralnetwork.dataClasses.NavItems
import com.example.neuralnetwork.firebase.FirebaseAuthRepo

class MainScreenViewModel(application: Application): AndroidViewModel(application) {
    val auth = FirebaseAuthRepo()

    var isLogged by mutableStateOf(false)
    val MainNavItems = listOf<NavItems>(
        NavItems(title = "Try", icon = R.drawable.flask_samplebgr),
        NavItems(title = "Inspect", icon = R.drawable.visualize_tiltedsquaresbgr),
        NavItems(title = "Settings", icon = R.drawable.setting_bgr)
    )

    fun checkLogged(){
        if (auth.currentUser()!=null){
            isLogged =true
        }else{
            isLogged=false
        }
    }
}