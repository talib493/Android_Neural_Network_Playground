package com.example.neuralnetwork.ui_Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.neuralnetwork.R
import com.example.neuralnetwork.ui.theme.edgeColor
import java.util.Locale

@Composable
fun LoadNetworkItem(
    modelName: String,
    dataset: String,
    accuracy: Float,
    createdAt: String,
    onLoad:()-> Unit
) {
    var expanded by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .animateContentSize().clickable(
                        onClick = {
                            expanded=!expanded
                        }
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray.copy(alpha = .18f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.memory_24px),
                            contentDescription = null,
                            tint = edgeColor
                        )

                        Spacer(Modifier.width(12.dp))

                        Text(
                            text = modelName,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Icon(
                            painter = painterResource(
                                if (expanded)
                                    R.drawable.arrow_drop_up_24px
                                else
                                    R.drawable.arrow_drop_down_24px
                            ),
                            contentDescription = "arrow",
                            tint = edgeColor
                        )

                    }

                    Spacer(Modifier.height(14.dp))

                    InfoRow("Dataset", dataset)
                    InfoRow("Accuracy", "${"%.2f".format(accuracy)} %")
                    InfoRow("Created", createdAt)

                    AnimatedVisibility(expanded) {

                        Column {

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 14.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {

                                AssistChip(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {onLoad()},
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.play_arrow_24px),
                                            contentDescription = "load model",
                                            tint = edgeColor
                                        )
                                    },
                                    label = {
                                        Text(text = "Load", color = edgeColor)
                                    })

                            }

                        }

                    }

                }

            }
        }
