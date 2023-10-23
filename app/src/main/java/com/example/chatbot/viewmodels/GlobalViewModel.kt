package com.example.chatbot.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.chatbot.models.chat.ChatMessage
import com.example.chatbot.models.chat.Conversation
import com.example.chatbot.models.user.UserData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class GlobalViewModel : ViewModel()
{
    val db = Firebase.firestore

    private var currentConversationId : String = ""

    fun SaveChat(chat : ChatMessage, coversationId : String?, userData: UserData?)
    {
        if(coversationId.isNullOrBlank())
        {
            if (userData != null) {
                db.collection("conversations")
                    .add(Conversation(title = chat.content, userID = userData.userId))
                    .addOnSuccessListener {
                            documentReference ->
                        currentConversationId = documentReference.id
                        Log.d("DATA", "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener{
                            e -> Log.w("ERROR", "Error adding document", e)
                    }
            }
        }
        db.collection("chats")
            .add(chat)
            .addOnSuccessListener {
                documentReference ->
                Log.d("DATA", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener{
                e -> Log.w("ERROR", "Error adding document", e)
            }
    }

}