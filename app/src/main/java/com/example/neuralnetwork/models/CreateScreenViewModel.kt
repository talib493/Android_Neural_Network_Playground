package com.example.neuralnetwork.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreateScreenViewModel: ViewModel() {
    var networkName by mutableStateOf("")
        private set

    fun updateNetworkName(value: String){
        networkName=value
    }


    // Dropdown Menu Options For DataSet
    val options = listOf("XOR","AND","NAND","OR")
    var isDropped by mutableStateOf(false)
    var isSelected by mutableStateOf(options[0])
    fun updateSelectedOption(selectedOption: Int) {
        isSelected = options[selectedOption]

    }
    // Learning Rate Slider
    var LearningRate by mutableStateOf(0f)

    fun updateLearningRate(value: Float){
        LearningRate=value
    }

    // Epochs Text Field

    var epochs by mutableStateOf("")

    fun updateEpochs(value: String){
        epochs=value
    }
}