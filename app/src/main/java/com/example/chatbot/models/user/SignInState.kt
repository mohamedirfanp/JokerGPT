package com.example.chatbot.models.user

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
