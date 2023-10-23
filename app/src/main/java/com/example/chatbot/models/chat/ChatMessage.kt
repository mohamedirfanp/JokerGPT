package com.example.chatbot.models.chat

import com.example.chatbot.constants.Role

data class ChatMessage(
    val content: String,
    val role: String
) {
    companion object {
        val initConv = ChatMessage(
            content = "Hi there, how you doing?",
            role = Role.assistance
        )
    }
}