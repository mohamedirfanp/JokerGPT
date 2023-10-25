package com.example.chatbot.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.chatbot.R
import com.example.chatbot.components.CardComponent
import com.example.chatbot.components.HistoryCardTile
import com.example.chatbot.constants.Screens
import com.example.chatbot.constants.Strings
import com.example.chatbot.models.user.UserData
import com.example.chatbot.viewmodels.ChatViewModel
import com.example.chatbot.viewmodels.GlobalViewModel

@Composable
fun HomeView(userData: UserData?, navController: NavHostController, globalViewModel: GlobalViewModel,onSignOut: () -> Unit) {

    globalViewModel.GetConversations(userData = userData)

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(45.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hey, ${userData?.username} 👋",
                color = Color.White,
                fontSize = 25.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(250.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopEnd)
            ) {
                IconButton(onClick = { expanded = !expanded }, modifier = Modifier.size(48.dp)) {
                    AsyncImage(
                        model = userData?.profilePictureUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Sign Out", fontSize = 18.sp) },
                        onClick = { onSignOut() }
                    )
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(25.dp)) {

            Text(
                text = Strings.homeHeaderText,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CardComponent(R.drawable.chat, Strings.homeChatWithBot) {

                    navController.navigate(Screens.chat)
                }
                CardComponent(R.drawable.speaking, Strings.homeTalkWithBot) {
                        navController.navigate(Screens.talk)
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "History",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "See all",
                        textDecoration = TextDecoration.Underline,
                        color = colorResource(
                            id = R.color.button_color
                        ),
                        fontSize = 20.sp
                    )
                }
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp))
            {
                items(globalViewModel.historyConversation)
                {
                    HistoryCardTile(chatType = it.chatType, title = it.title){
                        navController.navigate(Screens.chat + "/${it.conversationID}")
                    }
                }
            }
            if (globalViewModel.historyConversation.isEmpty())
            {
                Text(text = "No History", color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
        }


    }

}