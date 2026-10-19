package com.example.neuralnetwork.ai

import com.example.neuralnetwork.dataClasses.EpochResult
import com.example.neuralnetwork.dataClasses.TrainingData
import com.example.neuralnetwork.dataClasses.TrainingUiState
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.yield

class TrainingNetwork(
        private val network: NeuralNetwork,
    ) {
       suspend fun Train(
            samples: List<TrainingData>,
            epochs: Int,
            learningRate: Float,
            onEpochFinished: (TrainingUiState) -> Unit,
            onEachEpoch:(Int) -> Unit
        ) {

                var lastResult = TrainingUiState()

                repeat(epochs) { epoch ->

                    currentCoroutineContext().ensureActive()

                    val result = TrainEpoch(samples, learningRate)

                    lastResult = TrainingUiState(
                        epoch = epoch + 1,
                        loss = result.loss,
                        error = result.error,
                        accuracy = result.accuracy,
                        isTraining = true,
                        outWeightChange = result.OutweightChange,
                    )

                    if ((epoch + 1) % 100 == 0) {
                        onEpochFinished(lastResult)
                        yield()
                    }
                    onEachEpoch(
                        1
                    )

                }
           onEpochFinished(
                    lastResult.copy(isTraining = false)
                )
        }

        fun TrainEpoch(sample: List<TrainingData>,learningRate: Float): EpochResult {

            var totalLoss = 0f
            var totalError = 0f
            var correct =0f
            var uweightChange=0f
            for (data in sample) {
                val prediction = network.predict(data.input)
                val derivative = ActivationFunctions.sigmoidDerivative(prediction.values[0])
                val error = network.calculateError(prediction.values[0], data.target)
                val delta = derivative * error

                val neuron_output = network.outputNeuron()
                val learningRate = learningRate
                val hiddenOutput = network.hiddenOutput(data.input)
                val oldOutputWeights =neuron_output.weights.values.copyOf()
                val loss = 0.5f * error * error

                totalError += kotlin.math.abs(error)
                totalLoss += loss
                val predictedClass = if (prediction.values[0] >= 0.5f) 1f else 0f
                if (predictedClass==data.target){
                    correct++
                }

                for (i in neuron_output.weights.values.indices) {
                    val currentWeight = neuron_output.weights.values[i]
                    val weightChange = learningRate * delta * hiddenOutput.values[i]
                    uweightChange = weightChange

                    neuron_output.weights.values[i] += weightChange


                }
                neuron_output.bias += learningRate * delta

                for(neurons in 0 until network.hiddenLayerSize()){
                    val hiddenNeurons = network.hiddenNeuron(neurons)
                    val outputWeight =
                        oldOutputWeights[neurons]

                    val hiddenActivation =
                        hiddenOutput.values[neurons]

                    val deltaHidden =
                        delta * outputWeight *
                                ActivationFunctions.sigmoidDerivative(hiddenActivation)
                    for (j in hiddenNeurons.weights.values.indices){
                        val weightChange = learningRate*deltaHidden*data.input.values[j]
                        hiddenNeurons.weights.values[j]+=weightChange

                    }
                    hiddenNeurons.bias+=learningRate*deltaHidden





                }
            }
 return EpochResult(
    loss = totalLoss / sample.size,
    error = totalError / sample.size,
     accuracy = (correct / sample.size) * 100f,
     OutweightChange = uweightChange,
)
        }
    }
