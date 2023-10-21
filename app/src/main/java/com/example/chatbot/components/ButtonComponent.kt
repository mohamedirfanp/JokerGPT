package com.example.chatbot.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatbot.R


@Composable
fun ButtonComponent(name : String)
{
    Button(onClick = { /*TODO*/ }, modifier =Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_color)),shape = RoundedCornerShape(15.dp)) {
        Text(text = name, modifier = Modifier.padding(10.dp), fontSize = 20.sp)
    }
}