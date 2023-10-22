package com.example.chatbot.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.chatbot.R

@Composable
fun TopBar(name : String, onclick : () -> Unit)
{
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onclick() }, modifier = Modifier
            .size(50.dp)
            .background(
                color = colorResource(id = R.color.gray_bg),
                shape = RoundedCornerShape(25.dp)
            )
            .aspectRatio(1f)
        ) {
            Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "back", tint = Color.White, modifier = Modifier.size(35.dp))
        }

        Text(text = name, style = MaterialTheme.typography.headlineSmall, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }

}