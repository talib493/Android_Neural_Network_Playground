package com.example.neuralnetwork.ui_Components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.neuralnetwork.ui.theme.BabyPink
import com.example.neuralnetwork.ui.theme.centerColor
import com.example.neuralnetwork.ui.theme.edgeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String){
    TopAppBar(
        modifier = Modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = edgeColor,
            titleContentColor = centerColor
        ),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic
            )
        }
    )
}