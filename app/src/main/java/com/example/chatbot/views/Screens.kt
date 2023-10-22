package com.example.chatbot.views

import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.chatbot.viewmodels.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun Screens(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext: Context
) {
    val scope = rememberCoroutineScope()
    NavHost(navController = navController, startDestination = Screens.landing) {
        composable(route = Screens.home) {
            HomeView(
                userData = googleAuthUiClient.getSignedInUser(),
                navController,
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

        composable(route = Screens.chat) {
            val chatViewModel = hiltViewModel<ChatViewModel>()
            Chat(chatViewModel, navController)
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
    }
}