package com.example.neuralnetwork.dataClasses

data class EpochResult(
val loss: Float,
val error: Float,
val accuracy: Float,
val OutweightChange: Float,
)
