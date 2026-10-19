package com.example.neuralnetwork.dataClasses

data class TrainingUiState(
    val epoch: Int = 0,
    val loss : Float =0f,
    val error: Float = 0f,
    var accuracy: Float =0f,
    val outWeightChange: Float = 0f,
    val isTraining: Boolean = false,

    )