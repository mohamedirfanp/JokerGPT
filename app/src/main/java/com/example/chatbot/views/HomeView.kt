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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.chatbot.R
import com.example.chatbot.components.CardComponent
import com.example.chatbot.components.HistoryCardTile
import com.example.chatbot.models.user.UserData

data class HistoryCardData(
    val chatType: String,
    val title: String
)

@Composable
fun HomeView(userData: UserData?, onSignOut: () -> Unit) {

    val historyDataList = listOf(
        HistoryCardData("chat", "Chat Title 1 asodjaosmd osdaij "),
        HistoryCardData("speaking", "Speaking Title 1 asdoiajdpasmasdnasopdaso"),
        HistoryCardData("chat", "Chat Title 2 aspdjasodkas asdnoaspkd"),
        HistoryCardData("speaking", "Speaking Title 2 as doansp maspod")
        // Add more items as needed
    )

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
                        text = { Text("Sign Out") },
                        onClick = { onSignOut() }
                    )
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(25.dp)) {

            Text(
                text = "How may I Help You Today ?",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardComponent(R.drawable.speaking, "Talk with Bot")
                CardComponent(R.drawable.chat, "Chat with Bot")
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
//        HistoryCardTile("chat", "Lorem is testing the where is cut")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp))
            {
                items(historyDataList)
                {
                    HistoryCardTile(chatType = it.chatType, title = it.title)
                }
            }
        }


    }

}