package com.example.neuralnetwork.dataClasses

import java.util.UUID

data class FirebaseModelData(
    val modelName: String="",
    val modelId: String = UUID.randomUUID().toString(),
    val accuracy: Float=0f,
    val hiddenWeights: List<FirebaseWeightsStruct> = emptyList(),
    val hiddenBiases: List<Float> = emptyList(),

    val outputWeights: List<Float> = emptyList(),
    val outputBias: Float = 0f,

    val learningRate: Float =0f,
    val epochs: Int = 0,
    val totalEpochTrained: Int = 0,
    val inputNodes: Int=0,
    val hiddenNodes: Int=0,
    val outputNodes: Int = 0,
    val datasetName: String ="",
    val createdAt:Long = System.currentTimeMillis()


)