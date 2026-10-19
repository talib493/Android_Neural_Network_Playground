package com.example.neuralnetwork.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neuralnetwork.models.MyNetworksScreenViewModel
import com.example.neuralnetwork.navigation.Routes
import com.example.neuralnetwork.operationState.OperationState
import com.example.neuralnetwork.ui.theme.edgeColor
import com.example.neuralnetwork.ui_Components.MyNetworkItem
import com.example.neuralnetwork.ui_Components.TopAppBar

@Composable
fun MyNetworks(
    viewModel: MyNetworksScreenViewModel = viewModel(),
    onNavigateToTry: (Routes.Try) -> Unit,
    onNavigateToTrain: (Routes.Train) -> Unit,
    onNavigationToInspect: (Routes.Inspect)-> Unit
){
    val context = LocalContext.current
    val dataState = viewModel.dataStatus
    val deleteStatus = viewModel.deleteStatus

    LaunchedEffect(Unit) {
        viewModel.getData()
    }
    LaunchedEffect(dataState) {
        when(dataState){
            is OperationState.Success-> {
//                Toast.makeText(context,dataState.data, Toast.LENGTH_SHORT).show()
                viewModel.dataStatus= OperationState.Idle
            }

            is OperationState.Error-> {
                Toast.makeText(context,dataState.message, Toast.LENGTH_SHORT).show()
                viewModel.dataStatus = OperationState.Idle
            }

            else -> ""
        }
    }
    LaunchedEffect(deleteStatus) {
        when(deleteStatus){
            is OperationState.Success ->{
                Toast.makeText(context,deleteStatus.data, Toast.LENGTH_SHORT).show()
                viewModel.deleteStatus= OperationState.Idle
            }
            is OperationState.Error -> {
                Toast.makeText(context,deleteStatus.message, Toast.LENGTH_SHORT).show()
                viewModel.deleteStatus= OperationState.Idle
            }
            else -> {}
        }
    }
    val networkList = viewModel.networkList
    val isLoading = dataState is OperationState.Loading

    if (networkList.size==0 &&dataState != OperationState.Loading){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = "ERROR: You don't have any saved model")
        }
    }
    else if (dataState is OperationState.Error){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = "ERROR: ${dataState.message}")
        }
    }
    else
    Scaffold(
        containerColor = Color.White,
        contentColor = Color.Black,
        topBar = {TopAppBar("MY Networks")}
    ){innerPadding->
        Column(modifier = Modifier.padding(innerPadding).padding(8.dp).fillMaxSize()) {
            LazyColumn(modifier = Modifier) {
                items(networkList){item->
                        MyNetworkItem(
                            modelName = item.modelName,
                            dataset = item.datasetName,
                            accuracy = item.accuracy,
                            createdAt = viewModel.dateFormatter(createdAt = item.createdAt),
                            onTry = {
                                onNavigateToTry(
                                    Routes.Try(
                                        item.modelId
                                    )
                                )
                            },
                            onInspect = {
                                onNavigationToInspect(
                                    Routes.Inspect(
                                        id = item.modelId
                                    )
                                )
                            },
                            onTrain = {
                                onNavigateToTrain(
                                    Routes.Train(
                                        id = item.modelId
                                    )
                                )

                            },
                            onDelete = {
                                viewModel.deleteItem(item.modelId)
                                viewModel.getData()
                            }
                        )
                    }
                }
            }

        }
    if (isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = edgeColor,
                strokeWidth = 4.dp,
                trackColor = Color.LightGray
            )
        }
    }
    }
