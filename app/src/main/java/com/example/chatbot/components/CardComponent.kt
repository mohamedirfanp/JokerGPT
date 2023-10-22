package com.example.chatbot.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatbot.R

@Composable
fun CardComponent(imageResource: Int, title: String, onCardClick: () -> Unit) {

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF232323),
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .size(width = 180.dp, height = 200.dp)
            .clickable {
                onCardClick()
            }
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {

            Box(
                modifier = Modifier.background(
                    color = colorResource(id = R.color.button_color),
                    shape = RoundedCornerShape(25.dp)
                )
            ) {

                Icon(
                    painter = painterResource(id = imageResource), contentDescription = "",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(10.dp),

                    )
            }

            Icon(
                Icons.Filled.ArrowForward, contentDescription = "go_to", modifier = Modifier
                    .rotate(310f)
                    .padding(10.dp)
                    .size(35.dp),
                tint = Color.White
            )
        }

        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight(350),
            color = Color.White,
            lineHeight = 30.sp,
            modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
        )
    }
}