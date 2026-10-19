package com.example.neuralnetwork.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.neuralnetwork.firebase.FirebaseAuthRepo
import com.example.neuralnetwork.operationState.OperationState
import kotlinx.coroutines.flow.MutableStateFlow

class SettingScreenViewModel: ViewModel() {
    val firebaseAuth = FirebaseAuthRepo()

    val currentUser = firebaseAuth.currentUser()

    var isLogged = MutableStateFlow(if (currentUser!=null) true else false)
    val email = if (currentUser!=null) currentUser.email else "Guest@gmail.com"

    var emailState = MutableStateFlow(email?:"Guest@gmail.com")

    var signOutState by mutableStateOf<OperationState<String>>(OperationState.Idle)

    fun signOut(){
            firebaseAuth.logOut()
            signOutState = OperationState.Success("User signed out")
            isLogged.value = false
            emailState.value = "Guest@gmail.com"
    }

}