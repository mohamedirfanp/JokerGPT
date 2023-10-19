package com.example.chatbot.models

data class ChatRequest(
    val messages: List<ChatMessage>,
    val model: String
)