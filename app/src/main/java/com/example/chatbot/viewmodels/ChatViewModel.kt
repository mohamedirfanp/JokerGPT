package com.example.chatbot.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.constants.Role
import com.example.chatbot.models.chat.ChatMessage
import com.example.chatbot.models.chat.ChatRequest
import com.example.chatbot.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _state = MutableStateFlow(listOf(ChatMessage.initConv))
    val chats = _state.asStateFlow()

    var message: String by mutableStateOf("")

    fun emitMessage() {
        val myChat = ChatMessage(content = message, role = Role.me)
        viewModelScope.launch {
            _state.emit(_state.value + myChat)
            val response = repository.getResponse(ChatRequest(_state.value, "gpt-3.5-turbo"))
            val botChat = ChatMessage(
                content = response.choices[0].message.content.toString(),
                role = Role.assistance
            )
            _state.emit(_state.value + botChat)
        }
        message = ""
    }
}