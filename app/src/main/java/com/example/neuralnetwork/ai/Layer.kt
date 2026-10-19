package com.example.neuralnetwork.ai

class Layer(
    private val neurons : List<Neuron>
){
    fun predict (input:Vector):Vector{

        val result =FloatArray(neurons.size)
        for (i in neurons.indices){
            result[i]=neurons[i].predict(input)

        }
        return Vector(result)
    }

    fun getNeuron(index: Int): Neuron {
        return neurons[index]
    }

    fun size(): Int {
        return neurons.size
    }
}