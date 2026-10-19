package com.example.neuralnetwork.models

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neuralnetwork.ai.aiRepository
import com.example.neuralnetwork.dataClasses.FirebaseModelData
import com.example.neuralnetwork.dataClasses.TrainingUiState
import com.example.neuralnetwork.firebase.FirestoreRepo
import com.example.neuralnetwork.operationState.OperationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrainingScreenViewModel: ViewModel() {
    val ai = aiRepository()
    val firestore = FirestoreRepo()
    private val _uiState = MutableStateFlow(TrainingUiState())
    val uiState = _uiState.asStateFlow()
    var networkName by mutableStateOf("")
    var epoch by mutableStateOf("")

    var totalTrainedEpoch by mutableStateOf(0)
    var learningRate by mutableStateOf(0f)
    var dataset by mutableStateOf("")
    var network1 = ai.CreateNetwork()

    var saveState by mutableStateOf<OperationState<String>>(OperationState.Idle)
    var TrainModelStatus by mutableStateOf<OperationState<String>>(OperationState.Idle)
    var modelData by mutableStateOf<FirebaseModelData?>(null)
        private set

    var loadStatus by mutableStateOf<OperationState<String>>(OperationState.Idle)

    var updateState by mutableStateOf<OperationState<String>>(OperationState.Idle)

    var modelid = ""

    var FirebaseDataOnject = FirebaseModelData()

    fun train(scope: CoroutineScope){
        scope.launch(Dispatchers.Default) {
            try {
                ai.train(
                    network1,
                    dataset = dataset,
                    epochs = epoch.toInt(),
                    learningRate = learningRate,
                    onEpochFinished = {
                        state -> _uiState.value = state
                    },
                    onEachEpoch = { e ->
                        totalTrainedEpoch+=e
                        Log.d("EPOCHT",totalTrainedEpoch.toString())
                    }
                )
            } finally {
                Log.d("Training","Coroutine ended")
            }
        }
    }
    fun save(
        onSuccess:(FirebaseModelData)-> Unit
    ){
        try {
            val copy = FirebaseDataOnject.copy(
                modelName = networkName,
                accuracy = _uiState.value.accuracy,
                hiddenWeights = network1.hiddenWeights(),
                hiddenBiases = network1.hiddenBiases(),
                outputWeights = network1.outputNeuron().weights.values.toList(),
                outputBias = network1.outputNeuron().bias,
                epochs = epoch.toIntOrNull() ?: 0,
                totalEpochTrained = totalTrainedEpoch,
                inputNodes = 2,
                hiddenNodes = 3,
                outputNodes = 1,
                datasetName = dataset,
                learningRate = learningRate
            )
            FirebaseDataOnject = copy
            onSuccess(
                FirebaseDataOnject
            )
        }catch (e: Exception){

        }
    }
    fun saveNetwork(
        model: FirebaseModelData,
        OnSuccess:()-> Unit
    ){
        viewModelScope.launch(Dispatchers.IO){
            firestore.addModel(
                model,
                 onFailure = {text ->
                    saveState = OperationState.Error(text)

                }, onSuccess = {
                    saveState = OperationState.Success("Model Saved Successfully")
                    OnSuccess()
                }
            )
        }
    }
    fun check(
        onCheck: (Boolean) -> Unit
    ){
        firestore.checkExist(
            model = FirebaseModelData(
                modelName = networkName,
                accuracy = _uiState.value.accuracy,
                hiddenWeights = network1.hiddenWeights(),
                hiddenBiases = network1.hiddenBiases(),
                outputWeights = network1.outputNeuron().weights.values.toList(),
                outputBias = network1.outputNeuron().bias,
                epochs = epoch.toIntOrNull()?:0,
                totalEpochTrained = totalTrainedEpoch,
                inputNodes = 2,
                hiddenNodes = 3,
                outputNodes = 1,
                datasetName = dataset,
                learningRate = learningRate,
            ),
            Exist = {check->
                onCheck(check)
            }
        )
    }
    fun load(list: FirebaseModelData){
        val network = ai.CreateNetwork()
        totalTrainedEpoch = list.totalEpochTrained
        networkName = list.modelName
        dataset = list.datasetName
        epoch = list.epochs.toString()
        uiState.value.accuracy = list.accuracy
        learningRate = list.learningRate
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

    fun getModelWithId(modelId: String) {
        viewModelScope.launch {
            firestore.getModelWithId(
                id = modelId,
                onSuccess = { model ->
                    modelData = model
                    modelid = model.modelId
                    TrainModelStatus = OperationState.Success("Model Loaded Successfully")
                },
                onFailure = { error ->
                    TrainModelStatus = OperationState.Error(error)
                }
            )
        }
    }

    fun update(){
        firestore.updateModel(
            model = FirebaseModelData(
                modelId = modelid ,
                modelName = networkName,
                accuracy = _uiState.value.accuracy,
                hiddenWeights = network1.hiddenWeights(),
                hiddenBiases = network1.hiddenBiases(),
                outputWeights = network1.outputNeuron().weights.values.toList(),
                outputBias = network1.outputNeuron().bias,
                epochs = epoch.toIntOrNull()?:0,
                totalEpochTrained = totalTrainedEpoch,
                inputNodes = 2,
                hiddenNodes = 3,
                outputNodes = 1,
                datasetName = dataset,
                learningRate = learningRate,
            ),
            onSuccess = {
                updateState = OperationState.Success("Model Updated Successfully")
                getModelWithId(modelId = modelid)
            },
            onFailure = {error->
                updateState = OperationState.Error(error)
            }
        )
    }


    fun resertTrainState() {
        TrainModelStatus = OperationState.Idle
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