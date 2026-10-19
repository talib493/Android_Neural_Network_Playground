package com.example.neuralnetwork.models

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neuralnetwork.firebase.FirebaseAuthRepo
import com.example.neuralnetwork.operationState.OperationState
import kotlinx.coroutines.launch

class RegisterScreenViewModel() : ViewModel() {

    var registerState by mutableStateOf<OperationState<String>>(OperationState.Idle)
        private set
    var LoginState by mutableStateOf<OperationState<String>>(OperationState.Idle)
        private set
    val authRepo = FirebaseAuthRepo()

    var username by mutableStateOf("")
        private set

    fun UpdateUsername(value: String){
        username=value
    }
    var password by mutableStateOf("")
        private set

    fun UpdatePassword(value: String){
        password=value
    }

    var passVisibility by mutableStateOf(false)

    fun register(){
        if (username.isBlank()||password.isBlank()){
            viewModelScope.launch {
                registerState = OperationState.Error(message = "E-mail or Password can't be empty")
            }
            } else{
                viewModelScope.launch {
                    registerState = OperationState.Loading
                }
            authRepo.createUser(username,password, onSuccess = {
                viewModelScope.launch {
                    registerState = OperationState.Success("User Created Successfully")
                }
            }, onFailure = {text ->
                viewModelScope.launch {
                    registerState = OperationState.Error(text)
                }
            }
            )
        }
    }

    fun login(){
        if (username.isBlank()||password.isBlank()){
            viewModelScope.launch {
                LoginState = OperationState.Error(message = "E-mail or Password can't be empty")
            }
        } else{
             viewModelScope.launch {
                 LoginState= OperationState.Loading
             }
            authRepo.logInUser(email = username, password = password , onSuccess = {
                viewModelScope.launch {
                    LoginState = OperationState.Success("Logged In Successfully")
                }
            }, onFailure = {text ->
                viewModelScope.launch {
                    LoginState = OperationState.Error(text)
                }
            })
        }

    }

    fun resetLoginState(){
        LoginState = OperationState.Idle
    }
    fun resetRegisterState(){
        registerState = OperationState.Idle
    }
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }


}
