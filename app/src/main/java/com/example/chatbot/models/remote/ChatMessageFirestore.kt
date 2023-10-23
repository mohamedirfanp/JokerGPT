package com.example.chatbot.models.remote

import com.example.chatbot.constants.Role
import com.example.chatbot.models.chat.ChatMessage

data class ChatMessageFirestore(
    val content: String = "",
    val role: String = "",
    var conversationId: String? = null
)
{
    companion object {
        val initConv = ChatMessageFirestore(
            content = "",
            role = Role.system,
            conversationId = null
        )
    }
}

fun ChatMessageFirestore.toChatMessage() : ChatMessage
{
    return ChatMessage(
        content = content,
        role = role
    )
}
