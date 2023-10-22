package com.example.chatbot.models.chat

data class ChatRequest(
    val messages: List<ChatMessage>,
    val model: String
)