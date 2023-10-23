package com.example.chatbot.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chatbot.constants.Role
import com.example.chatbot.models.chat.ChatMessage

@Composable
fun ChatTile(chat: ChatMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .align(if (chat.role == Role.me) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (chat.role == Role.me) 48f else 0f,
                        bottomEnd = if (chat.role == Role.me) 0f else 48f
                    )
                )
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(text = chat.content)
        }
    }
}