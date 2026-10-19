package com.example.neuralnetwork.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neuralnetwork.R
import com.example.neuralnetwork.models.TrainingScreenViewModel
import com.example.neuralnetwork.navigation.Routes
import com.example.neuralnetwork.operationState.OperationState
import com.example.neuralnetwork.ui.theme.edgeColor
import com.example.neuralnetwork.ui_Components.InfoRow

@Composable
fun TrainScreen(
    args: Routes.Train,
    viewModel: TrainingScreenViewModel = viewModel()
) {
    val save = viewModel.saveState

    val context = LocalContext.current
    val trainState = viewModel.TrainModelStatus
    var dialog by remember { mutableStateOf(false) }
    val loadState = viewModel.loadStatus
    val state by viewModel.uiState.collectAsState()
    val totalEpoch = viewModel.epoch.toFloatOrNull()?:1f
    val accuracy = state.accuracy
    val scope = rememberCoroutineScope()
    val epochLong = (viewModel.epoch.toLongOrNull()?:0)/100
    val updateState = viewModel.updateState

    val status = remember(state.epoch) {
        when {
            state.epoch==0 -> "0% Training Completed"
            state.epoch >= epochLong * 100 -> "Training Complete"
            state.epoch >= epochLong * 75 -> "75% Training Completed"
            state.epoch >= epochLong * 50 -> "50% Training Completed"
            state.epoch >= epochLong * 25 -> "25% Training Completed"
            else ->""
        }
    }


    LaunchedEffect(args.id) {
        if (args.id.isNotEmpty()) {
            viewModel.getModelWithId(args.id)
        }
    }

    // Update Model State
    LaunchedEffect(updateState) {
        when (updateState) {
            is OperationState.Success -> {
                Toast.makeText(context, updateState.data, Toast.LENGTH_SHORT).show()
                viewModel.updateState = OperationState.Idle
            }

            is OperationState.Error -> {
                Toast.makeText(context, updateState.message, Toast.LENGTH_SHORT).show()
                viewModel.updateState = OperationState.Idle
            }

            else -> ""
        }
    }

    // Training Status
    LaunchedEffect(trainState) {
        when(trainState){
            is OperationState.Success -> {
                if (viewModel.modelData!=null){
                    viewModel.load(list = viewModel.modelData!!)
                    viewModel.resertTrainState()
                }
            }
            is OperationState.Error -> {
                Toast.makeText(context,trainState.message, Toast.LENGTH_SHORT).show()
                viewModel.resertTrainState()
            }
            else -> ""
        }
    }

    // Loading Model Data Into Network State
    LaunchedEffect(loadState) {
        when(loadState){
            is OperationState.Success -> {
                Toast.makeText(context,loadState.data, Toast.LENGTH_SHORT).show()
                viewModel.loadStatus = OperationState.Idle
            }
            is OperationState.Error -> {
                Toast.makeText(context,loadState.message, Toast.LENGTH_SHORT).show()
                viewModel.loadStatus = OperationState.Idle
            }
            else -> ""
        }
    }

    // Passed args update
    LaunchedEffect(args) {
        viewModel.networkName=args.networkName
        viewModel.epoch = args.epochs
        viewModel.learningRate =args.learningRate
        viewModel.dataset = args.dataset
    }

    // Save State
    LaunchedEffect(save) {
        when(save){
            is OperationState.Success-> {
                Toast.makeText(context,save.data,Toast.LENGTH_SHORT).show()
                viewModel.saveState = OperationState.Idle
            }
            is OperationState.Error -> {
                Toast.makeText(context,save.message, Toast.LENGTH_SHORT).show()
                viewModel.saveState = OperationState.Idle
            }

            else -> {}
        }

    }

    // UI
    Scaffold(
        containerColor = Color(0xFFF5F7FB)
    ) { innerPadding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(18.dp)

        ) {

            Text(
                viewModel.networkName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                "Train your neural network",
                color = Color.Gray
            )

            Spacer(Modifier.height(20.dp))


            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {

                Column(
                    Modifier.padding(20.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.graph_3_24px),
                            contentDescription = "training graph",
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            "Training Progress",
                            fontWeight = FontWeight.Bold
                        )

                    }

                    Spacer(Modifier.height(20.dp))

                    LinearProgressIndicator(

                        progress = { (state.epoch.toFloat()/totalEpoch).coerceIn(0f,1f) },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),

                        color = Color.White

                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "Epoch : ${state.epoch} / ${viewModel.epoch}"
                    )

                }

            }

            Spacer(Modifier.height(20.dp))


            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {

                Column(
                    Modifier.padding(20.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.analytics_24px),
                            contentDescription = "training analytics",
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            "Statistics",
                            fontWeight = FontWeight.Bold
                        )

                    }

                    Spacer(Modifier.height(18.dp))

                    InfoRow("Accuracy", accuracy.toString()+"%")

                    InfoRow("Error", String.format("%.2f", state.error))

                    InfoRow("Loss", String.format("%.2f", state.loss))

                    InfoRow("Status", if (state.isTraining)"Training" else "Not Training")

                }

            }

            Spacer(Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {

                Column(
                    Modifier.padding(20.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.settings_24px),
                            contentDescription = "training settings",
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            "Configuration",
                            fontWeight = FontWeight.Bold
                        )

                    }

                    Spacer(Modifier.height(16.dp))

                    InfoRow("Dataset", viewModel.dataset)
                    InfoRow("Learning Rate", String.format("%.2f", viewModel.learningRate))
                    InfoRow("Epochs", viewModel.totalTrainedEpoch.toString())

                }

            }

            Spacer(Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {

                Column(
                    Modifier.padding(20.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                       Icon(
                            painter = painterResource(R.drawable.article_24px),
                            contentDescription = "training report",
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            "Training Log",
                            fontWeight = FontWeight.Bold
                        )

                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "Log : ${status}",
                        color = Color.White
                    )

                }

            }

            Spacer(Modifier.height(28.dp))

            Button(
                enabled =!state.isTraining,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = edgeColor,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray
                ),

                onClick = {
                    viewModel.train(scope)
                }

            ) {

                Icon(
                    painter = painterResource(R.drawable.play_arrow_24px),
                    contentDescription = "training start",
                        )

                Spacer(Modifier.width(8.dp))

                Text(
                    "Start Training"
                )

            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Color.Gray
                ),
                enabled = !state.isTraining,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                shape = RoundedCornerShape(16.dp),

                onClick = {
                    if (viewModel.isInternetAvailable(context)) {
                        viewModel.check(
                            onCheck = { s ->
                                if (s) {
                                    dialog = true
                                } else {
                                    viewModel.save(
                                        onSuccess = { model ->
                                            viewModel.saveNetwork(
                                                model,
                                                OnSuccess = {
                                                    viewModel.getModelWithId(model.modelId)
                                                }
                                            )
                                        }
                                    )
                                }
                            }
                        )
                    } else{
                        Toast.makeText(context,"No Internet Connection", Toast.LENGTH_SHORT).show()
                    }
                }

            ) {

             Icon(
                 painter = painterResource(R.drawable.save_24px),
                 contentDescription = "save",
                 tint = edgeColor)

                Spacer(Modifier.width(8.dp))

                Text(
                    "Save Model", color = Color.Black
                )

            }

        }

    }
    if (dialog){
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = {dialog=false},
            title = {
                Text(text = "Collision Detected", style = MaterialTheme.typography.titleLarge, color = Color.Black)
            },
            text = {
                Text(text = "A Model With Same Name Already Exist To Over write it press Confirm ", style = MaterialTheme.typography.bodyMedium, color = Color.Black )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.update()
                        dialog=false
                    }
                ) {
                    Text("Confirm", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dialog=false
                    }
                ) {
                    Text("Cancel",color = Color.Black)
                }
            }
        )
    }

}
