package com.example.chatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.chatbot.network.ChatService
import com.example.chatbot.repo.Repository
import com.example.chatbot.ui.theme.ChatBotTheme
import com.example.chatbot.util.GoogleAuthUiClient
import com.example.chatbot.viewmodels.TalkViewModel
import com.example.chatbot.viewmodels.VoiceToTextParser
import com.example.chatbot.views.Screens
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatBotTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF010101)

                ) {
                    val navHostController = rememberNavController()
                    Screens(
                        navController = navHostController,
                        googleAuthUiClient,
                        applicationContext,
                        application
                    )
                }
            }
        }
    }
}

