package com.example.neuralnetwork.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neuralnetwork.ai.Vector
import com.example.neuralnetwork.models.TryScreenViewModel
import com.example.neuralnetwork.navigation.Routes
import com.example.neuralnetwork.operationState.OperationState
import com.example.neuralnetwork.ui.theme.edgeColor
import com.example.neuralnetwork.ui_Components.InfoRow
import com.example.neuralnetwork.ui_Components.LoadNetworkItem
import com.example.neuralnetwork.ui_Components.TopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TryScreen(
    viewModel: TryScreenViewModel = viewModel(),
    args: Routes.Try
) {
    val context = LocalContext.current

    val networks = viewModel.networkList
    val dataState = viewModel.dataStatus
    val tryDataState = viewModel.TryModelStatus
    var load by remember { mutableStateOf(false) }
    val loadState = viewModel.loadStatus

    LaunchedEffect(args.id) {
        if (args.id.isNotEmpty()) {
            viewModel.getModelWithId(args.id)
        }
    }

    LaunchedEffect(loadState) {
        when(loadState){
            is OperationState.Success -> {
                Toast.makeText(context,loadState.data, Toast.LENGTH_SHORT).show()
                viewModel.loadStatus = OperationState.Idle
            }
        else -> ""
        }
    }

    LaunchedEffect(tryDataState) {
        when (tryDataState) {
            is OperationState.Success -> {
                if (viewModel.modelData != null) {
                    viewModel.load(viewModel.modelData!!)
                    Toast.makeText(context,tryDataState.data, Toast.LENGTH_SHORT).show()
                    viewModel.clearStatus()
                }
            }
            is OperationState.Error -> {
                Toast.makeText(context, tryDataState.message, Toast.LENGTH_SHORT).show()

                viewModel.clearStatus()
            }
            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getData()
    }

    LaunchedEffect(dataState) {
        when(dataState){
            is OperationState.Success-> {
                load=true
                viewModel.dataStatus= OperationState.Idle
            }

            is OperationState.Error-> {
                Toast.makeText(context,dataState.message, Toast.LENGTH_SHORT).show()
                viewModel.dataStatus = OperationState.Idle
            }

            else -> ""
        }
    }


    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar("Try Network")
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray.copy(alpha = .12f)
                ),
                border = BorderStroke(1.dp, edgeColor)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = viewModel.modelName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    HorizontalDivider()

                    InfoRow("Dataset", viewModel.dataset)
                    InfoRow("Accuracy", "${"%.2f".format(viewModel.accuracy)} %")

                }

            }

            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray.copy(alpha = .12f)
                ),
                border = BorderStroke(1.dp, edgeColor)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        "Input A",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        FilterChip(
                            selected = viewModel.InputAStatus0,
                            onClick = {
                                viewModel.InputAStatus0 = true
                                viewModel.InputAStatus1 = false
                            },
                            label = { Text(text = "0") }
                        )

                        FilterChip(
                            selected = viewModel.InputAStatus1,
                            onClick = {
                                viewModel.InputAStatus1 = true
                                viewModel.InputAStatus0 = false
                            },
                            label = { Text("1") }
                        )

                    }

                    Spacer(Modifier.height(20.dp))

                    Text(
                        "Input B",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        FilterChip(
                            selected = viewModel.InputBStatus0,
                            onClick = {
                                viewModel.InputBStatus0 = true
                                viewModel.InputBStatus1 = false
                            },
                            label = { Text("0") }
                        )

                        FilterChip(
                            selected = viewModel.InputBStatus1,
                            onClick = {
                                viewModel.InputBStatus1 = true
                                viewModel.InputBStatus0 = false
                            },
                            label = { Text("1") }
                        )

                    }

                }

            }

            Spacer(Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

            Button(
                modifier = Modifier
                    .height(55.dp)
                    .weight(2f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = edgeColor,
                    contentColor = Color.White
                ),
                onClick =
                    {
                        if (viewModel.modelName != "-- -- --"){

                          val answer =  viewModel.network1.predict(Vector(floatArrayOf(viewModel.inputA.toFloat(),viewModel.inputB.toFloat())))

                            viewModel.output = answer.values[0]

                            Log.d("NETWORK", "INPUTA:"+viewModel.inputA+" INPUTB:"+viewModel.inputB.toString())
                            Log.d("NETWORK", "HiddenW"+viewModel.network1.hiddenWeights().toString())
                            Log.d("NETWORK","HiddenB"+viewModel.network1.hiddenBiases().toString())
                            Log.d("NETWORK","OutputW"+viewModel.network1.OutputNeuronweights().contentToString())
                            Log.d("NETWORK","OutputB"+viewModel.network1.outputNeuron().bias.toString())
                            Log.d("NETWORK","ANSWER:"+answer.values.contentToString())
                        }
                        else{
                            Toast.makeText(context,"No Model Loaded",Toast.LENGTH_SHORT).show()
                        }
                    }
            ) {

                Text(
                    "Predict",
                    style = MaterialTheme.typography.titleMedium
                )

            }
                Spacer(modifier = Modifier.width(6.dp))

                Button(
                    enabled = load,
                    modifier = Modifier
                        .height(55.dp)
                        .weight(2f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = edgeColor,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    ),
                    onClick =
                        {
                            if (!load||networks.size==0){
                                Toast.makeText(context, "No models available to load ",Toast.LENGTH_SHORT).show()
                            }else
                            viewModel.BottomSheetState=true
                        }
                ) {
                    Text(
                        "Load Model",
                        style = MaterialTheme.typography.titleMedium
                    )

                }
        }

            Spacer(modifier = Modifier.height(15.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray.copy(alpha = .12f)
                ),
                border = BorderStroke(1.dp, edgeColor)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        "Prediction",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    HorizontalDivider()

                    InfoRow(
                        "Output",
                        viewModel.output.let { "%.4f".format(it) } ?: "--"
                    )

                    InfoRow(
                        "Classification",
                        when {
                            viewModel.output == null -> "--"
                            viewModel.output >= 0.5f -> "TRUE"
                            else -> "FALSE"
                        }
                    )

                }

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

