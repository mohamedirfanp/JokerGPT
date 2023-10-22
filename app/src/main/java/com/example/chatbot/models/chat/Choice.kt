package com.example.chatbot.models.chat

data class Choice(
    val finish_reason: String,
    val index: Int,
    val message: Message
)