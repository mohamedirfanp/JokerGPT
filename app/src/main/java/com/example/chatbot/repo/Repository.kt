package com.example.chatbot.repo

import com.example.chatbot.R
import com.example.chatbot.constants.API_KEY
import com.example.chatbot.models.ChatRequest
import com.example.chatbot.models.ChatResponse
import com.example.chatbot.network.ChatService

class Repository(private val chatService : ChatService)
{
    suspend fun getResponse(chatRequest: ChatRequest) = chatService.getChatResponse("Bearer ${API_KEY}",chatRequest)
}