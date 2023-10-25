package com.example.chatbot.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.chatbot.R
import com.example.chatbot.components.TopBar
import com.example.chatbot.constants.Screens
import com.example.chatbot.viewmodels.VoiceToTextParser

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TalkView(navController: NavController, voiceToTextParser: VoiceToTextParser)
{
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.voicewaves)
    )
    val state by voiceToTextParser.state.collectAsStateWithLifecycle()


    Column( modifier = Modifier
        .fillMaxSize()
        .padding(top = 25.dp, bottom = 30.dp, start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),) {
        TopBar(name = "Talk with Me") {
            navController.navigate(Screens.home)
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){


            Box(modifier = Modifier.fillMaxWidth()) {


                LottieAnimation(composition = composition,iterations = LottieConstants.IterateForever, isPlaying = state.isSpeaking)
            }


            IconButton(onClick = {
                if(state.isSpeaking)
                {
                    voiceToTextParser.stopListening()
                }
                else
                {
                    voiceToTextParser.startListening()
                }
            },
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        color = Color.Transparent,
                        shape = CircleShape
                    )
                    .border(2.dp, colorResource(id = R.color.button_color), CircleShape)
                    .fillMaxWidth()) {
                Icon( if (!state.isSpeaking) {
                    Icons.Default.Mic
                } else {
                    Icons.Default.Stop
                }  , contentDescription = "Mic", tint = colorResource(id = R.color.button_color), modifier = Modifier.size(40.dp))
            }
        }
    }
}