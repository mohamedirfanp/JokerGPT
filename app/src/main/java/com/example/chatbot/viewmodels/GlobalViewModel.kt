package com.example.chatbot.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chatbot.models.chat.ChatMessage
import com.example.chatbot.models.remote.Conversation
import com.example.chatbot.models.user.UserData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class GlobalViewModel : ViewModel()
{
    private val db = Firebase.firestore

    var historyConversation: List<Conversation> by mutableStateOf(emptyList())

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



}