package com.example.chatbot.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatbot.R
import com.example.chatbot.components.ButtonComponent
import com.example.chatbot.constants.Strings
import com.example.chatbot.models.user.SignInState

@Composable
fun LandingView(state: SignInState, onSignInClick: () -> Unit) {

    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 40.dp, end = 40.dp, top = 60.dp, bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Image(
            painter = painterResource(id = R.drawable.robot_image),
            contentDescription = "robot_logo",
            modifier = Modifier.size(300.dp)
        )

        Text(
            text = Strings.landingHighlightText,
            color = Color.White,
            fontSize = 40.sp,
            lineHeight = 50.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(500)
        )

        Text(
            text = Strings.landingSupportText,
            color = Color.White,
            fontSize = 18.sp,
            lineHeight = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(250)
        )

        ButtonComponent(name = Strings.getStarted) {
            onSignInClick()
        }
    }
}