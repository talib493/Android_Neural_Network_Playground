package com.example.neuralnetwork.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neuralnetwork.R
import com.example.neuralnetwork.models.CreateScreenViewModel
import com.example.neuralnetwork.navigation.Routes
import com.example.neuralnetwork.ui.theme.edgeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    viewModel: CreateScreenViewModel = viewModel(),
    onNavigationToTrain: (Routes.Train) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xFFF5F7FB),
        ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 18.dp)
                .verticalScroll(rememberScrollState())
        ) {


            Text(
                text = "Create Neural Network",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Configure your model before training.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier
                        .padding(20.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.memory_24px),
                            contentDescription = null,
                            tint = edgeColor
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            "Network Name",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),

                        value = viewModel.networkName,

                        onValueChange = {
                            viewModel.updateNetworkName(it)
                        },

                        placeholder = {
                            Text("Example : XOR Model")
                        },

                        singleLine = true,

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = edgeColor,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Gray
                        )
                    )

                    Spacer(Modifier.height(24.dp))



                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.dataset_24px),
                            contentDescription = null,
                            tint = edgeColor
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            "Dataset",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                    }

                    Spacer(Modifier.height(8.dp))

                    ExposedDropdownMenuBox(

                        expanded = viewModel.isDropped,

                        onExpandedChange = {
                            viewModel.isDropped = it
                        }

                    ) {

                        OutlinedTextField(

                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),

                            readOnly = true,

                            value = viewModel.isSelected,

                            onValueChange = {},

                            trailingIcon = {

                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = viewModel.isDropped
                                )

                            },

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = edgeColor,
                                unfocusedBorderColor = Color.LightGray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Gray
                            )

                        )

                        ExposedDropdownMenu(

                            expanded = viewModel.isDropped,

                            onDismissRequest = {
                                viewModel.isDropped = false
                            }

                        ) {

                            viewModel.options.forEachIndexed { index, item ->

                                DropdownMenuItem(

                                    text = {
                                        Text(item)
                                    },

                                    onClick = {

                                        viewModel.updateSelectedOption(index)

                                        viewModel.isDropped = false

                                    }

                                )

                            }

                        }

                    }

                    Spacer(modifier = Modifier.height(28.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.speed_24px),
                                contentDescription = null,
                                tint = edgeColor
                            )

                            Spacer(Modifier.width(8.dp))

                            Text(
                                "Learning Rate",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )

                        }

                        Text(
                            text = String.format("%.2f", viewModel.LearningRate),
                            color = edgeColor,
                            fontWeight = FontWeight.Bold
                        )

                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Controls how fast the network learns.",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Slider(

                        modifier = Modifier.fillMaxWidth(),

                        value = viewModel.LearningRate,

                        onValueChange = {

                            viewModel.updateLearningRate(it)

                        },

                        colors = SliderDefaults.colors(

                            thumbColor = edgeColor,

                            activeTrackColor = edgeColor

                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.refresh_24px),
                            contentDescription = null,
                            tint = edgeColor
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Epochs",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Number of training iterations.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(

                        modifier = Modifier.fillMaxWidth(),

                        value = viewModel.epochs,

                        onValueChange = {
                            viewModel.updateEpochs(it)
                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),

                        singleLine = true,

                        placeholder = {
                            Text("1000")
                        },

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = edgeColor,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = if (viewModel.epochs.toLongOrNull()?:0>1000000||viewModel.epochs.toLongOrNull()?:0<1000)Color.Red else Color.Green,
                            unfocusedTextColor = Color.Gray
                        )

                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = edgeColor.copy(alpha = 0.08f)
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.lightbulb_24px),
                                contentDescription = null,
                                tint = edgeColor
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "The network architecture is predefined for XOR. You only need to configure the training parameters.",
                                style = MaterialTheme.typography.bodySmall
                            )

                        }

                    }

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = edgeColor
                ),

                onClick = {
                    if(viewModel.networkName.isBlank()){
                        Toast.makeText(context,"Give your network a name first",Toast.LENGTH_SHORT).show()
                    }
                    else if (viewModel.LearningRate==0f){
                        Toast.makeText(context,"Network can't learn at  0 leaning rate",Toast.LENGTH_SHORT).show()
                    }
                    else if (viewModel.epochs.isBlank()||viewModel.epochs.toLong()<1000){
                        Toast.makeText(context,"Training iteration can't be below 1000",Toast.LENGTH_SHORT).show()
                    }else if (viewModel.epochs.toLong()>1000000 ){
                        Toast.makeText(context,"Maximum epoch limit is capped at 1 million",Toast.LENGTH_SHORT).show()
                    }
                    else
                    onNavigationToTrain(
                        Routes.Train(
                            networkName = viewModel.networkName,
                            learningRate = viewModel.LearningRate,
                            dataset = viewModel.isSelected,
                            epochs = viewModel.epochs
                        )
                    )

                }

            ) {

                Icon(
                    painter = painterResource(R.drawable.add_circle_24px),
                    contentDescription = null,
                    tint= Color.White
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Create Network",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

        }

    }

}