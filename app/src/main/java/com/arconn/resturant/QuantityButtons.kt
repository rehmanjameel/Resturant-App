package com.arconn.resturant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuantityButtons(
    food: MenuFood,
    onQuantitySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (number in 1..15) {
            Box(
                modifier = Modifier
                    .width(42.dp)
                    .height(42.dp)
                    .background(
                        color = food.color,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        onQuantitySelected(number)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}