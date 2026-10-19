package com.example.neuralnetwork.ai

import kotlin.random.Random

class Neuron(
    inputSize: Int,

    ) {
     val weights = Vector(FloatArray(inputSize){
        Random.nextFloat() * 2f - 1f
    }
    )
    var bias  = Random.nextFloat() * 2f - 1f

    fun predict(input: Vector): Float {

        require(input.values.size == weights.values.size) {
            "Input size and weight size must match"
        }

        val weightedSum =
            input.dot(weights) + bias

        return ActivationFunctions.sigmoid(weightedSum)
    }
}