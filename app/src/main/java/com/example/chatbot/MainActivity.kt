package com.example.chatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.chatbot.models.ChatMessage
import com.example.chatbot.ui.theme.ChatBotTheme
import com.example.chatbot.viewmodels.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hiltVm = ViewModelProvider(this).get(ChatViewModel::class.java)


        setContent {
            ChatBotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val chats = hiltVm.chats.observeAsState()
                    Column {

                        TextField(value = hiltVm.message, onValueChange = {
                            hiltVm.message = it
                        })

                        Button(onClick = {
                            chats.value?.add(ChatMessage(hiltVm.message, "user"))
                            hiltVm.getChatResponse()
                        }) {
                            Text(text = "Get")
                        }


                        LazyColumn()
                        {
                            items(chats.value!!){
                                if(it.role == "user")
                                    Text(text = it.content, color = Color.Red)
                                else
                                    Text(text = it.content, color = Color.Green)
                            }
                        }
                    }
                }
            }
        }
    }
}

