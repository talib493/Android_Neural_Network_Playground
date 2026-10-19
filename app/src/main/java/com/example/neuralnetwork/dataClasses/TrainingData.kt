package com.example.neuralnetwork.dataClasses

import com.example.neuralnetwork.ai.Vector

data class TrainingData(
    val input: Vector,
    val target : Float,
)