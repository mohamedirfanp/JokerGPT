package com.example.chatbot.models.chat

import com.example.chatbot.constants.Role
import com.example.chatbot.models.remote.ChatMessageFirestore

data class ChatMessage(
    val content: String,
    val role: String
) {
    companion object {
        val initConv = ChatMessage(
            content = "Hi there, how you doing?",
            role = Role.system
        )
    }
}

fun ChatMessage.toChatMessageFireStore() : ChatMessageFirestore {
    return ChatMessageFirestore(
        content = content,
        role = role,
        conversationId = null
    )
}

