package com.example.chatbot.views

import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatbot.constants.Screens
import com.example.chatbot.util.GoogleAuthUiClient
import com.example.chatbot.viewmodels.ChatViewModel
import com.example.chatbot.viewmodels.GlobalViewModel
import com.example.chatbot.viewmodels.SignInViewModel
import com.example.chatbot.viewmodels.TalkViewModel
import com.example.chatbot.viewmodels.VoiceToTextParser
import kotlinx.coroutines.launch

@Composable
fun Screens(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext: Context,
    application: Application
) {
    val scope = rememberCoroutineScope()
    val chatViewModel = hiltViewModel<ChatViewModel>()
    val globalViewModel = viewModel<GlobalViewModel>()
    val talkViewModel = hiltViewModel<TalkViewModel>()
    val voiceToTextParser = VoiceToTextParser(application, talkViewModel, applicationContext)
    NavHost(navController = navController, startDestination = Screens.landing) {
        composable(route = Screens.home) {
            HomeView(
                userData = googleAuthUiClient.getSignedInUser(),
                navController,
                globalViewModel = globalViewModel,
                onSignOut = {
                    scope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            applicationContext,
                            "Signed out",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate(Screens.landing)
                    }
                }
            )
        }

        composable(route = Screens.chat + "/{conversationId}",
                ) {

//            chatViewModel.removeMsg()
            val conversation = chatViewModel.chats.collectAsState()
            val conversationId = it.arguments?.getString("conversationId")


            Chat(chatViewModel, conversation.value, navController,googleAuthUiClient.getSignedInUser(), conversationId)
        }

        composable(route = Screens.chat){
//            chatViewModel.removeMsg()
            val conversation = chatViewModel.chats.collectAsState()
            Chat(
                chatViewModel = chatViewModel,
                conversation = conversation.value,
                navController = navController,
                userData = googleAuthUiClient.getSignedInUser(),
                conversationId = null
            )
        }

        composable(route = Screens.landing) {
            val signInViewModel = viewModel<SignInViewModel>()
            val state by signInViewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate(Screens.home)
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        scope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            signInViewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate(Screens.home)
                    signInViewModel.resetState()
                }
            }

            LandingView(
                state = state,
                onSignInClick = {
                    scope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }

         composable(route = Screens.talk) {
             TalkView(navController = navController, voiceToTextParser = voiceToTextParser)
         }
    }
}