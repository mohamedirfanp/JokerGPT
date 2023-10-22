package com.example.chatbot.network

import com.example.chatbot.models.chat.ChatRequest
import com.example.chatbot.models.chat.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ChatService {

    @POST("completions")
    suspend fun getChatResponse(
        @Header("Authorization") token: String,
        @Body chatRequest: ChatRequest
    ): ChatResponse


}