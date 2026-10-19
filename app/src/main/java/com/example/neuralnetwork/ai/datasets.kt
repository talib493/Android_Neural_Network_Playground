package com.example.neuralnetwork.ai

import com.example.neuralnetwork.dataClasses.TrainingData

class datasets {
    val xorData = listOf(
        TrainingData(
            input = Vector(floatArrayOf(0f, 0f)),
            target = 0f
        ),

        TrainingData(
            input = Vector(floatArrayOf(0f, 1f)),
            target = 1f
        ),

        TrainingData(
            input = Vector(floatArrayOf(1f, 0f)),
            target = 1f
        ),

        TrainingData(
            input = Vector(floatArrayOf(1f, 1f)),
            target = 0f
        )
    )
    val andData = listOf(
        TrainingData(
            input = Vector(floatArrayOf(0f, 0f)),
            target = 0f
        ),

        TrainingData(
            input = Vector(floatArrayOf(0f, 1f)),
            target = 0f
        ),

        TrainingData(
            input = Vector(floatArrayOf(1f, 0f)),
            target = 0f
        ),

        TrainingData(
            input = Vector(floatArrayOf(1f, 1f)),
            target = 1f
        )
    )
    val orData = listOf(
        TrainingData(
            input = Vector(floatArrayOf(0f, 0f)),
            target = 0f
        ),

        TrainingData(
            input = Vector(floatArrayOf(0f, 1f)),
            target = 1f
        ),

        TrainingData(
            input = Vector(floatArrayOf(1f, 0f)),
            target = 1f
        ),

        TrainingData(
            input = Vector(floatArrayOf(1f, 1f)),
            target = 1f
        )
    )
    val nandData = listOf(
        TrainingData(
            input = Vector(floatArrayOf(0f, 0f)),
            target = 1f
        ),

        TrainingData(
            input = Vector(floatArrayOf(0f, 1f)),
            target = 1f
        ),

        TrainingData(
            input = Vector(floatArrayOf(1f, 0f)),
            target = 1f
        ),

        TrainingData(
            input = Vector(floatArrayOf(1f, 1f)),
            target = 0f
        )
    )
}