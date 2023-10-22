package com.example.chatbot.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.chatbot.R
import com.example.chatbot.models.chat.ChatMessage

@Composable
fun ChatTile(chat : ChatMessage)
{
    val width: Float = if (chat.role == "user") 1f else 0.8f

    Box(
        modifier = Modifier
            .fillMaxWidth(width)
            .background(color = colorResource(id = R.color.gray_bg), shape = if (chat.role == "user") RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp, bottomStart = 25.dp) else RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp, bottomEnd = 25.dp)),
        ) {
        Text(
            text = chat.content,
            textAlign = if (chat.role == "user") TextAlign.End else TextAlign.Start,
            color = Color.White,
            modifier = Modifier.padding(15.dp)

        )
    }
}