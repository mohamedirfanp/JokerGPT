package com.example.chatbot.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatbot.R
import com.example.chatbot.components.ChatTile
import com.example.chatbot.components.SampleChatTile
import com.example.chatbot.components.TopBar
import com.example.chatbot.constants.Screens
import com.example.chatbot.constants.Strings
import com.example.chatbot.models.chat.ChatMessage
import com.example.chatbot.viewmodels.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Chat(
    chatViewModel: ChatViewModel,
    conversation: List<ChatMessage>,
    navController: NavHostController
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()

    LaunchedEffect(conversation.size) {
        listState.animateScrollToItem(conversation.size)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp, bottom = 30.dp, start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(50.dp),
    ) {

        TopBar(name = "Chat with Bot") {
            navController.navigate(Screens.home)
        }

        if (conversation.isEmpty()) {
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Example",
                    color = Color.White,
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.headlineMedium
                )

                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    Strings.staticMessage.map { message ->
                        SampleChatTile(message = message) {
                            chatViewModel.message = message
                        }
                    }
                }

            }
        } else {
            conversation.let { chatMessages ->
                LazyColumn(
                    Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                    state = listState
                ) {
                    items(chatMessages) { message ->
                        ChatTile(message)
                    }
                }
            }

        }
        OutlinedTextField(
            value = chatViewModel.message, onValueChange = { updatedValue ->
                chatViewModel.message = updatedValue
            },
            trailingIcon = {
                IconButton(onClick = {
                    chatViewModel.emitMessage()
                }) {
                    Icon(
                        Icons.Filled.Send, contentDescription = "Send",
                        tint = colorResource(id = R.color.button_color)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() })
        )
    }
}