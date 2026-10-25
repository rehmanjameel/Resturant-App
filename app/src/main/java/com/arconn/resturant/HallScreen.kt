package com.arconn.resturant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import com.arconn.resturant.roomdb.Hall

@Composable
fun HallScreen(
    modifier: Modifier = Modifier,
    halls: List<Hall>,
    onHallClick: (Hall) -> Unit,
    onAddHall: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Select Hall",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(halls, key = { it.id }) { hall ->
                val hallId = hall.hallNumber
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onHallClick(hall)
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.hall_icon
                            ),
                            contentDescription = "Hall $hallId",
                            modifier = Modifier.size(120.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = "Hall $hallId",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        Button(
            onClick = onAddHall,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "+ Add Hall",
                fontSize = 18.sp
            )
        }
    }
}