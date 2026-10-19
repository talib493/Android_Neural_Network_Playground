package com.example.neuralnetwork.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neuralnetwork.dataClasses.FirebaseModelData
import com.example.neuralnetwork.models.InspectScreenViewModel
import com.example.neuralnetwork.navigation.Routes
import com.example.neuralnetwork.operationState.OperationState
import com.example.neuralnetwork.ui_Components.CardSection
import com.example.neuralnetwork.ui_Components.InfoRow
import com.example.neuralnetwork.ui_Components.LoadNetworkItem
import com.example.neuralnetwork.ui_Components.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inspectScreen(
    viewModel: InspectScreenViewModel = viewModel(),
    args: Routes.Inspect
){
    val networks = viewModel.networkList
    val model = viewModel.firebaseObject
    val context = LocalContext.current

    val loadStatus = viewModel.loadStatus
    val getModelStatus = viewModel.getModelStatus

    LaunchedEffect(args.id) {
        if (args.id.isNotEmpty()){
            viewModel.getModelWithId(args.id)
        }
    }
    LaunchedEffect(getModelStatus) {
        when(getModelStatus){
            is OperationState.Success -> {
                if (viewModel.modelData!=null){
                    viewModel.load(viewModel.modelData!!)
                }
            }
            is OperationState.Error ->{
                Toast.makeText(context,getModelStatus.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    LaunchedEffect(loadStatus) {

        when(loadStatus){
            is OperationState.Success -> {
                Toast.makeText(context,loadStatus.data,Toast.LENGTH_SHORT).show()
                viewModel.loadStatus = OperationState.Idle

            }
            else -> {}
        }

    }


    LaunchedEffect(Unit) {
        viewModel.getData()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar("Model Inspector")
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            CardSection("General") {

                InfoRow("Model", model.modelName)
                InfoRow("Dataset", model.datasetName)
                InfoRow("Accuracy", "${model.accuracy}%")
                InfoRow("Learning Rate", model.learningRate.toString())
                InfoRow("Total Epochs", model.totalEpochTrained.toString())
                InfoRow("Created", viewModel.dateFormatter(model.createdAt))

            }


            CardSection("Architecture") {

                InfoRow("Input Neurons", model.inputNodes.toString())
                InfoRow("Hidden Neurons", model.hiddenNodes.toString())
                InfoRow("Output Neurons", model.outputNodes.toString())

            }


            CardSection("Hidden Layer") {

                model.hiddenWeights.forEachIndexed { index, weight ->
                    InfoRow(
                        "Neuron:",
                        "${index + 1}"
                    )
                    weight.weights.forEachIndexed { i,  w ->
                        InfoRow(
                            "W${i + 1}",
                            w.toString()
                        )
                    }
                }

            }


            CardSection("Hidden Biases") {
                model.hiddenBiases.forEachIndexed { index, bias ->

                    InfoRow(
                        "B${index + 1}",
                        String.format("%.4f", bias)
                    )

                }

            }


            CardSection("Hidden → Output Layer") {

                model.outputWeights.forEachIndexed { index, weight ->

                    InfoRow(
                        "W${index + 1}",
                        weight.toString()
                    )

                }

            }

            CardSection("Output Bias") {

                InfoRow(
                    "Bias",
                    String.format("%.4f", model.outputBias)
                )

            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth().height(60.dp),
                onClick = {
                viewModel.BottomSheetState=true
            }) {
                Text("Load Network", style = MaterialTheme.typography.titleMedium , color= Color.Black)
            }


        }

    }
    if (viewModel.BottomSheetState){

        ModalBottomSheet(
            onDismissRequest = {viewModel.BottomSheetState=false},
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "MY Models", style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(modifier = Modifier.fillMaxWidth()) {

                    items(networks) { item ->
                        LoadNetworkItem (
                            modelName = item.modelName,
                            dataset = item.datasetName,
                            accuracy = item.accuracy,
                            createdAt = viewModel.dateFormatter(createdAt = item.createdAt),
                            onLoad = {
                                viewModel.load(item)
                                viewModel.BottomSheetState = false
                            }
                        )

                    }


                }
            }
        }

    }

}

