package com.example.neuralnetwork.ai;

import androidx.collection.floatListOf
import com.example.neuralnetwork.dataClasses.FirebaseWeightsStruct

class NeuralNetwork(
        private val hiddenLayer:Layer,
        private val  outputLayer:Layer
) {
        fun predict(input: Vector): Vector {
                val hiddenOutput = hiddenLayer.predict(input)
                return outputLayer.predict(hiddenOutput)
        }
        fun calculateError(prediction: Float , target: Float):Float{
                return target-prediction
        }

        fun outputNeuron(): Neuron {
                return outputLayer.getNeuron(0)
        }

        fun OutputNeuronweights(): FloatArray {
                return outputNeuron().weights.values
        }
        fun hiddenOutput(input: Vector): Vector {
                return hiddenLayer.predict(input)
        }

        fun hiddenLayerSize(): Int {
                return hiddenLayer.size()
        }

        fun hiddenNeuron(index: Int): Neuron {
                return hiddenLayer.getNeuron(index = index)
        }
        fun neuronsHidden(): List<Neuron>{
                val neurons = mutableListOf<Neuron>()
                for (i in 0 until hiddenLayer.size()){
                        neurons.add(hiddenLayer.getNeuron(i))
                }
                return neurons
        }

        fun hiddenWeights(): List<FirebaseWeightsStruct>{
                val neurons = neuronsHidden()
                return neurons.map {
                        FirebaseWeightsStruct(
                                it.weights.values.toList()
                        )
                }
        }
        fun hiddenBiases(): List<Float>{
                val neurons = neuronsHidden()
                return neurons.map {
                        it.bias
                }
        }


}
