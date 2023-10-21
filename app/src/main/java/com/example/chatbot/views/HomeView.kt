package com.example.chatbot.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatbot.R
import com.example.chatbot.components.CardComponent
import com.example.chatbot.components.HistoryCardTile

data class HistoryCardData(
    val chatType: String,
    val title: String
)
@Composable

fun HomeView()
{

    val historyDataList = listOf(
        HistoryCardData("chat", "Chat Title 1 asodjaosmd osdaij "),
        HistoryCardData("speaking", "Speaking Title 1 asdoiajdpasmasdnasopdaso"),
        HistoryCardData("chat", "Chat Title 2 aspdjasodkas asdnoaspkd"),
        HistoryCardData("speaking", "Speaking Title 2 as doansp maspod")
        // Add more items as needed
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(45.dp)
        ) {

        // TopBar
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
            Text(text = "Hey, Brandie 👋", color = Color.White, fontSize = 25.sp)

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(48.dp)) {
                Icon(
                    Icons.Filled.AccountCircle,
                    contentDescription = "profile_photo",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.Gray
                        )
                        .clip(CircleShape)

                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(25.dp)) {

        Text(text = "How may I Help You Today ?", style = MaterialTheme.typography.labelMedium, color = Color.White)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            CardComponent(R.drawable.speaking, "Talk with Bot")
            CardComponent(R.drawable.chat, "Chat with Bot")
        }
        }
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = "History", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "See all", textDecoration = TextDecoration.Underline, color = colorResource(
                    id = R.color.button_color
                ), fontSize = 20.sp)
            }
        }
//        HistoryCardTile("chat", "Lorem is testing the where is cut")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp))
            {
                items(historyDataList)
                {
                    HistoryCardTile(chatType = it.chatType, title = it.title )
                }
            }
        }


    }

}