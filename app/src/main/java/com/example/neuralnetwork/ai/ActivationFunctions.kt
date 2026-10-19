package com.example.neuralnetwork.ai

import kotlin.math.exp

object ActivationFunctions{

    fun sigmoid(x: Float): Float {
        return (1f / (1f + exp(-x.toDouble()))).toFloat()
    }
    fun sigmoidDerivative(output: Float): Float {
        return output * (1f - output)
    }
    fun relu(x: Float): Float {
        return maxOf(0f, x)
    }

}