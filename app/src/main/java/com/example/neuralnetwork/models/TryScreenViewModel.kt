package com.example.neuralnetwork.models

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neuralnetwork.ai.aiRepository
import com.example.neuralnetwork.dataClasses.FirebaseModelData
import com.example.neuralnetwork.firebase.FirestoreRepo
import com.example.neuralnetwork.operationState.OperationState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TryScreenViewModel: ViewModel() {

    val firestore = FirestoreRepo()

    val ai  = aiRepository()
    var network1 = ai.CreateNetwork()
    var networkList by mutableStateOf<List<FirebaseModelData>>(emptyList())
        private set

    var InputAStatus0 by mutableStateOf(true)
    var InputAStatus1 by mutableStateOf(false)

    var InputBStatus0 by mutableStateOf(false)

    var InputBStatus1 by mutableStateOf(true)

    val inputA by derivedStateOf { if (InputAStatus0) 0 else 1 }
    val inputB by derivedStateOf { (if (InputBStatus0) 0 else 1) }

    var output by mutableStateOf(0f)

    var BottomSheetState by mutableStateOf(false)

    var dataStatus by mutableStateOf<OperationState<String>>(OperationState.Idle)
    var TryModelStatus by mutableStateOf<OperationState<String>>(OperationState.Idle)


    var modelData by mutableStateOf<FirebaseModelData?>(null)
        private set

    var modelName by mutableStateOf("-- -- --")
    var dataset by mutableStateOf("-- --")
    var accuracy by mutableStateOf(0f)
    var loadStatus by mutableStateOf<OperationState<String>>(OperationState.Idle)

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
        val network = ai.CreateNetwork()
        modelName = list.modelName
        dataset = list.datasetName
        accuracy = list.accuracy
            network.outputNeuron().bias = list.outputBias
            for (l in 0 until network.outputNeuron().weights.values.size){
                network.outputNeuron().weights.values[l] = list.outputWeights[l]
            }
            for (i in 0 until network.neuronsHidden().size) {
                network.hiddenNeuron(i).bias=list.hiddenBiases[i]
                for (k in 0 until network.hiddenNeuron(i).weights.values.size) {
                    network.hiddenNeuron(i).weights.values[k] = list.hiddenWeights[i].weights[k]
                }
            }
        network1=network
        loadStatus = OperationState.Success("${list.modelName} Loaded Successfully")
    }
    fun getModelWithId(modelId: String){
        viewModelScope.launch {
            firestore.getModelWithId(
                id = modelId,
                onSuccess = { model ->
                    modelData = model
                    TryModelStatus = OperationState.Success("Model Loaded Successfully")
                },
                onFailure = { error ->
                    TryModelStatus = OperationState.Error(error)
                }
            )
        }
    }

    fun clearStatus() {
        TryModelStatus = OperationState.Idle
    }
}
