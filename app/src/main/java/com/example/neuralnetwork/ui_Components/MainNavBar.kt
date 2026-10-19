package com.example.neuralnetwork.ui_Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.neuralnetwork.dataClasses.NavItems
import com.example.neuralnetwork.ui.theme.BabyPink

@Composable
fun MainNavBar(items: List<NavItems>,onPressed:(Int)-> Unit) {
    val edgeColor = Color(0xFF0D0626)

    NavigationBar(
        modifier = Modifier.height(80.dp).clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
        containerColor = edgeColor
    ){
        items.forEachIndexed { index, items ->
            NavigationBarItem(
                selected = false,
                onClick = {
                },
                label = { Text(text = items.title, color = Color.White) },
                icon = {
                    Image(painter = painterResource(items.icon),
                        contentDescription = items.title,
                        modifier = Modifier.size(30.dp)
                            .clickable(onClick = {
                                onPressed(index)
                            })
                    )
                },
                modifier = Modifier.size(30.dp),
                colors = NavigationBarItemDefaults.colors(
                                   )
            )
        }
    }

}
