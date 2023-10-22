package com.example.chatbot.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.models.chat.ChatMessage
import com.example.chatbot.models.chat.ChatRequest
import com.example.chatbot.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val chats = MutableLiveData<MutableList<ChatMessage>>()


    var message: String by mutableStateOf("")

    init {
        chats.value = ArrayList()
//        chats.value?.add(ChatMessage("This is a random message with more than 30 characters.", "user"))
//        chats.value?.add(ChatMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "assistance"))
//        chats.value?.add(ChatMessage("Random text for the third message. It's longer than 30 characters for sure.", "user"))
//        chats.value?.add(ChatMessage("Assistance message with over 30 characters in its content. Just for testing.", "assistance"))

    }

    fun getChatResponse() {
        viewModelScope.launch {
            try {
                val response = repository.getResponse(ChatRequest(chats.value!!, "gpt-3.5-turbo"))
                Log.d("RESPONSE", response.toString())
                chats.value?.add(
                    ChatMessage(
                        response.choices[0].message.content.toString(),
                        "assistant"
                    )
                )
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
            }
        }
    }
}