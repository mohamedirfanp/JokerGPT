package com.example.chatbot.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.constants.Role
import com.example.chatbot.constants.Strings
import com.example.chatbot.models.chat.ChatMessage
import com.example.chatbot.models.chat.ChatRequest
import com.example.chatbot.models.chat.toChatMessageFireStore
import com.example.chatbot.models.remote.ChatMessageFirestore
import com.example.chatbot.models.remote.Conversation
import com.example.chatbot.models.remote.toChatMessage
import com.example.chatbot.models.user.UserData
import com.example.chatbot.repo.Repository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor
    (private val repository: Repository,
     savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<String>(Strings.PARAM_CONVERSATION_ID)?.let{
            Log.d("PARAM", it)
        }

    }

    private val _state = MutableStateFlow(listOf(ChatMessage.initConv))

    var historyConversation: List<Conversation> by mutableStateOf(emptyList())

    val chats = _state.asStateFlow()

    var message: String by mutableStateOf("")

    private val db = Firebase.firestore

    private var currentConversationId : String? = null

    fun emitMessage(userData: UserData?)
    {
        val myChat = ChatMessage(content = message, role = Role.me)
        SaveChat(chat = myChat.toChatMessageFireStore(), userData = userData)
        viewModelScope.launch {
            _state.emit(_state.value + myChat)
            try {
                Log.d("DATA PASSING", _state.value.toString())
                val response = repository.getResponse(ChatRequest(messages = _state.value, "gpt-3.5-turbo"))

                val botChat = ChatMessage(
                    content = response.choices[0].message.content.toString(),
                    role = Role.assistance
                )
                SaveChat(chat = botChat.toChatMessageFireStore(), userData = userData)

                _state.emit(_state.value + botChat)
                message = ""
            } catch (e: Exception) {
                Log.d("ERROR IN API", e.message.toString())
            }
        }
    }

    fun SaveChat(chat : ChatMessageFirestore, userData: UserData?)
    {
        if(currentConversationId.isNullOrBlank())
        {
            if (userData != null) {
                db.collection("conversations")
                    .add(Conversation(title = chat.content, userID = userData.userId, chatType = "chat"))
                    .addOnSuccessListener {
                            documentReference ->
                        currentConversationId = documentReference.id
                        chat.conversationId = currentConversationId
                        addChatMessage(chat = chat)

                        Log.d("DATA", "CONVERSATION DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener{
                            e -> Log.w("ERROR", "Error adding document", e)
                    }
            }
        }
        else
        {
            chat.conversationId = currentConversationId
            addChatMessage(chat = chat)
        }
    }

    fun addChatMessage(chat: ChatMessageFirestore)
    {
        db.collection("chats")
            .add(chat)
            .addOnSuccessListener { documentReference ->
                Log.d("DATA", "CHAT DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("ERROR", "Error adding chat message document", e)
            }
    }

    fun GetConversations(userData: UserData?)
    {
        userData?.let {
            db.collection("conversations")
                .whereEqualTo("userID", userData.userId)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val conversationList = mutableListOf<Conversation>()
                    for (document in querySnapshot) {
                        val conversationData = document.toObject(Conversation::class.java)
                        val conversationId = document.id
                        conversationData.conversationID = conversationId
                        conversationList.add(conversationData)
                    }

                    historyConversation = conversationList
                }
                .addOnFailureListener { e ->
                    // Handle any errors that may occur during the query
                    Log.w("ERROR", "Error querying conversations: $e")
                }
        }

    }

    fun GetChats(conversationId : String)
    {
        db.collection("chats")
            .whereEqualTo("conversationId", conversationId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val chats = mutableListOf<ChatMessage>()

                for (document in querySnapshot) {
                    val conversationData = document.toObject(ChatMessageFirestore::class.java)
                    val conversationId = document.id
                    currentConversationId = conversationId
                    chats.add(conversationData.toChatMessage())
                }
                Log.d("DATA FROM CHATS EXISTS", chats.toString())
                chats.add(_state.value[0])
                _state.value = chats.asReversed()
            }
            .addOnFailureListener { e ->
                // Handle any errors that may occur during the query
                Log.w("ERROR", "Error querying conversations: $e")
            }
    }

}