package com.example.chatbot.viewmodels


import androidx.lifecycle.ViewModel
import com.example.chatbot.constants.Role
import com.example.chatbot.models.chat.ChatMessage
import com.example.chatbot.models.chat.ChatRequest
import com.example.chatbot.models.chat.ChatResponse
import com.example.chatbot.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TalkViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    suspend fun emitMessage(message: String) = GlobalScope.async {
        val chats: MutableList<ChatMessage> = mutableListOf()
        chats.add(ChatMessage(content = message, role = Role.me))
        var response: ChatResponse? = null
        response = repository.getResponse(ChatRequest(chats, "gpt-3.5-turbo"))
        if (response == null) {
            ""
        }
        response!!.choices[0].message.content
    }
}