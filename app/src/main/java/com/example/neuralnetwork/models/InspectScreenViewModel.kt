package com.example.neuralnetwork.models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.neuralnetwork.ai.aiRepository
import com.example.neuralnetwork.dataClasses.FirebaseModelData
import com.example.neuralnetwork.firebase.FirestoreRepo
import com.example.neuralnetwork.operationState.OperationState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InspectScreenViewModel: ViewModel() {
    val firestore = FirestoreRepo()
    var firebaseObject = FirebaseModelData()

    var networkList by mutableStateOf<List<FirebaseModelData>>(emptyList())
    private set

    var dataStatus by mutableStateOf<OperationState<String>>(OperationState.Idle)
    var loadStatus by mutableStateOf<OperationState<String>>(OperationState.Idle)

    var BottomSheetState by mutableStateOf(false)

    var modelData by mutableStateOf<FirebaseModelData?>(null)
        private set
    var getModelStatus by mutableStateOf<OperationState<String>>(OperationState.Idle)


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
    fun dateFormatter(createdAt: Long): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return formatter.format(Date(createdAt))
    }
    fun load(list: FirebaseModelData){
        val update = firebaseObject.copy(
            modelName = list.modelName,
            datasetName = list.datasetName,
            accuracy = list.accuracy ,
            outputBias = list.outputBias,
            outputWeights = list.outputWeights,
            outputNodes = list.outputNodes,
            hiddenNodes = list.hiddenNodes,
            hiddenBiases = list.hiddenBiases,
            hiddenWeights = list.hiddenWeights,
            createdAt = list.createdAt,
            learningRate = list.learningRate,
            totalEpochTrained = list.totalEpochTrained,
            inputNodes = list.inputNodes
        )
        firebaseObject = update
        Log.d("LOAD",firebaseObject.hiddenWeights.toString())
        loadStatus = OperationState.Success("${list.modelName} Loaded Successfully")
    }

    fun getModelWithId(modelId: String){
        viewModelScope.launch {
            firestore.getModelWithId(
                id = modelId,
                onSuccess = { model ->
                    modelData = model
                    getModelStatus = OperationState.Success("")
                },
                onFailure = { error ->
                    getModelStatus = OperationState.Error(error)
                }
            )
        }
    }
}