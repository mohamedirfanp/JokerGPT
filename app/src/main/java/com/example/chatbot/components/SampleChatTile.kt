package com.example.chatbot.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun SampleChatTile(message : String, onclick:() -> Unit) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF232323),
        ),
        border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clickable {
                onclick()
            }
    ) {
        Text(text = message, style = MaterialTheme.typography.titleLarge, color = Color.White, modifier = Modifier.padding(10.dp).fillMaxWidth(), textAlign = TextAlign.Center)
    }
}