package com.example.chatbot.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.models.ChatMessage
import com.example.chatbot.models.ChatRequest
import com.example.chatbot.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val chats = MutableLiveData<MutableList<ChatMessage>>()

    var message: String by mutableStateOf("")

    init {
        chats.value = ArrayList()
    }

    fun getChatResponse()
    {
        viewModelScope.launch {
            try {
            val response = repository.getResponse(ChatRequest(chats.value!!, "gpt-3.5-turbo"))
            Log.d("RESPONSE", response.toString())
                chats.value?.add(ChatMessage(response.choices[0].message.content.toString(),"assistant"))
            }
            catch (e : Exception)
            {
                Log.d("ERROR", e.toString())
            }
        }
    }
}