package com.example.chatbot.network

import com.example.chatbot.constants.BASE_URL
import com.example.chatbot.models.ChatRequest
import com.example.chatbot.models.ChatResponse
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ChatService {

    @POST("completions")
    suspend fun getChatResponse (
        @Header("Authorization") token : String,
        @Body chatRequest : ChatRequest
    ) : ChatResponse



}