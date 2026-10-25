
package com.arconn.resturant

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MenuFood(
    val id: Int,
    val name: String,
    val price: Int,
    val color: Color
)

@Composable
fun OrdersScreen(
    hallId: Int,
    tableId: Int,
    onBack: () -> Unit
) {
    val menu = remember {
        listOf(
            MenuFood(1, "چکن کباب", 80, Color(0xFF315BC5)),
            MenuFood(2, "اسپیشل چکن", 200, Color(0xFFD92E25)),
            MenuFood(3, "چکن چلی", 80, Color(0xFF087E70)),
            MenuFood(4, "چائنیز فل پلیٹ", 250, Color(0xFFC638A8)),
            MenuFood(5, "چائنیز ہاف", 150, Color(0xFFE3C52B)),
            MenuFood(6, "چائنیز مکس", 300, Color(0xFF263747)),
            MenuFood(7, "خمیری روٹی", 25, Color(0xFF14C927)),
            MenuFood(8, "سادہ روٹی", 20, Color(0xFFD83EBC)),
            MenuFood(9, "چپاتی روٹی", 20, Color(0xFF20BEB8)),
            MenuFood(10, "رائتہ", 30, Color(0xFF168C79))
        )
    }

    val quantities = remember {
        mutableStateMapOf<Int, Int>()
    }

    val grossTotal by remember {
        derivedStateOf {
            menu.sumOf { food ->
                food.price * (quantities[food.id] ?: 0)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF2EEDC))
                .padding(8.dp)
        ) {
            // Use the compact layout on phones.
            val compact = maxWidth < 600.dp

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                // Header: back icon and selected table
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector =
                                Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                    Text(
                        text = "H$hallId / T$tableId",
                        modifier = Modifier.weight(1f),
                        fontSize = if (compact) 16.sp else 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Rs. $grossTotal",
                        fontSize = if (compact) 16.sp else 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Keep actions on a separate line on phones.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { quantities.clear() },
                        modifier = Modifier.weight(1f).height(42.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB3261E)
                        )
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            // Connect to Room when order saving is added.
                        },
                        modifier = Modifier.weight(1f).height(42.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Save Order")
                    }
                }

                // Menu list
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(menu, key = { it.id }) { food ->

                        // Portrait: name/price above the buttons.
                        // Landscape/tablet: name, price and buttons in one row.
                        if (compact) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color.White.copy(alpha = 0.45f),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(6.dp),
                                verticalArrangement =
                                    Arrangement.spacedBy(5.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = food.name,
                                        modifier = Modifier.weight(1f),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF6E174F)
                                    )

                                    Text(
                                        text = "Rs. ${food.price}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                QuantityButtons(
                                    food = food,
                                    onQuantitySelected = { number ->
                                        quantities[food.id] =
                                            (quantities[food.id] ?: 0) +
                                                    number
                                    }
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {
                                Text(
                                    text = food.name,
                                    modifier = Modifier.width(145.dp),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6E174F)
                                )

                                Text(
                                    text = "${food.price}",
                                    modifier = Modifier.width(48.dp),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                QuantityButtons(
                                    food = food,
                                    onQuantitySelected = { number ->
                                        quantities[food.id] =
                                            (quantities[food.id] ?: 0) +
                                                    number
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


