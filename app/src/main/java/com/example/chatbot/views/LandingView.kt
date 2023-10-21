package com.example.chatbot.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.chatbot.R
import com.example.chatbot.components.ButtonComponent

@Composable
fun LandingView()
{
    Column(modifier = Modifier
        .fillMaxSize()

        .padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        
        Image(painter = painterResource(id = R.drawable.robot_image), contentDescription = "robot_logo",
            modifier = Modifier.size(300.dp))
        
        Text(text = "AI ChatBot Who Really Helps", color = Color.White, fontSize = 40.sp, lineHeight = 60.sp, textAlign = TextAlign.Center, fontWeight = FontWeight(500))

        Text(text = "Joker is the most friendly and fast chatbot ever made on Internet", color = Color.White, fontSize = 18.sp, lineHeight = 25.sp, textAlign = TextAlign.Center, fontWeight = FontWeight(250))
        
        ButtonComponent(name = "Get Started")

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

            Text(text = "Already have an account,", color = Color.White, fontSize = 18.sp)
            TextButton(onClick = { /*TODO*/ },) {
                Text(text = "Login", fontSize = 20.sp, textDecoration = TextDecoration.Underline, color = colorResource(
                    id = R.color.button_color
                ))
            }
        }
    }
}