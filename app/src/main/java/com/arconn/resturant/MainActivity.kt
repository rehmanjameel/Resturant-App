
package com.arconn.resturant

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.arconn.resturant.roomdb.AppDatabase
import com.arconn.resturant.roomdb.RestaurantRepository
import com.arconn.resturant.ui.theme.ResturantTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ResturantTheme {
                RestaurantApp()
            }
        }
    }
}

@Composable
fun RestaurantApp() {
    val context = LocalContext.current

    val database = remember {
        AppDatabase.getDatabase(context)
    }

    val repository = remember {
        RestaurantRepository(database)
    }

    val scope = rememberCoroutineScope()

    val halls by repository.observeHalls()
        .collectAsState(initial = emptyList())

    var selectedHallDbId by remember {
        mutableIntStateOf(0)
    }

    var selectedHallNumber by remember {
        mutableIntStateOf(1)
    }

    var selectedTable by remember {
        mutableIntStateOf(1)
    }

    var currentScreen by remember {
        mutableStateOf("halls")
    }

    val tables by repository
        .observeTables(selectedHallDbId)
        .collectAsState(initial = emptyList())

    LaunchedEffect(repository) {
        repository.initializeRestaurant()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        when (currentScreen) {

            "halls" -> {
                HallScreen(
                    modifier = Modifier.padding(innerPadding),
                    halls = halls,

                    onHallClick = { hall ->
                        selectedHallDbId = hall.id
                        selectedHallNumber = hall.hallNumber
                        currentScreen = "tables"
                    },

                    onAddHall = {
                        scope.launch {
                            repository.addHall()
                        }
                    }
                )
            }

            "tables" -> {
                TableScreen(
                    modifier = Modifier.padding(innerPadding),
                    hallId = selectedHallNumber,
                    tables = tables,

                    onBack = {
                        currentScreen = "halls"
                    },

                    onAddTable = {
                        scope.launch {
                            repository.addTable(selectedHallDbId)
                        }
                    },

                    onTableClick = { tableNumber ->
                        selectedTable = tableNumber
                        currentScreen = "orders"
                    }
                )
            }

            "orders" -> {
                OrdersScreen(
                    hallId = selectedHallNumber,
                    tableId = selectedTable,
                    onBack = {
                        currentScreen = "tables"
                    }
                )
            }
        }
    }
}




