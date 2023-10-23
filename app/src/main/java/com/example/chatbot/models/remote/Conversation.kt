package com.example.chatbot.models.remote

data class Conversation (
    val title : String = "",
    val userID : String = "",
    val chatType : String = "",
    var conversationID : String? = null
)