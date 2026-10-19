package com.example.neuralnetwork.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.neuralnetwork.dataClasses.FirebaseModelData
import com.example.neuralnetwork.firebase.FirebaseAuthRepo
import com.example.neuralnetwork.firebase.FirestoreRepo
import com.example.neuralnetwork.operationState.OperationState
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyNetworksScreenViewModel:ViewModel() {
    val firestore = FirestoreRepo()

    var networkList by mutableStateOf<List<FirebaseModelData>>(emptyList())
        private set
    var dataStatus by mutableStateOf<OperationState<String>>(OperationState.Idle)
    var deleteStatus by mutableStateOf<OperationState<String>>(OperationState.Idle)



    fun getData(){
        dataStatus = OperationState.Loading
        firestore.getModel(onSuccess = { l -> networkList=l
            dataStatus = OperationState.Success("All Models Loaded")
        },
            onFailure = {e->
                dataStatus = OperationState.Error(e)

            }
        )
    }
    fun deleteItem(id: String){
        firestore.deleteModel(modelId = id,
            onSuccess = {
                deleteStatus = OperationState.Success("Model Deleted Successfully")
            },
            onFailure = {error->
                deleteStatus = OperationState.Error(message = error)
            })
    }
    fun dateFormatter(createdAt: Long): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return formatter.format(Date(createdAt))
    }






}