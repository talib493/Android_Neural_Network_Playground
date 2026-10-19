package com.example.neuralnetwork.ai

import com.example.neuralnetwork.dataClasses.TrainingData
import com.example.neuralnetwork.dataClasses.TrainingUiState

class aiRepository {
    val Datasets = datasets()
    fun CreateNetwork(): NeuralNetwork{
        val network = NeuralNetwork(
            hiddenLayer = Layer(listOf(
                Neuron(2),
                Neuron(2),
                Neuron(2)
            )
            ),
            outputLayer = Layer(
                listOf(Neuron(3))
            )
        )
        return network
    }


    fun returnDataSet(dataSetObserver: String): List<TrainingData>{
        var current = dataSetObserver
        var set = listOf<TrainingData>()
        when{
            current=="XOR"-> set = Datasets.xorData
            current=="AND"-> set = Datasets.andData
            current=="OR" -> set = Datasets.orData
            current=="NAND"-> set = Datasets.nandData
        }
    return set
    }

    suspend fun train(network: NeuralNetwork ,dataset: String,epochs: Int,learningRate: Float,onEpochFinished: (TrainingUiState) -> Unit,onEachEpoch:(Int)->Unit){
        val train = TrainingNetwork(
            network
        )
        train.Train(
            samples = returnDataSet(dataset),
            epochs = epochs,
            learningRate,
            onEpochFinished = onEpochFinished,
            onEachEpoch = onEachEpoch
        )
    }



}