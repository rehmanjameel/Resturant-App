package com.arconn.resturant.roomdb

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow

class RestaurantRepository(
    private val database: AppDatabase
) {
    private val dao = database.restaurantDao()

    fun observeHalls(): Flow<List<Hall>> =
        dao.observeHalls()

    fun observeTables(hallId: Int): Flow<List<RestaurantTable>> =
        dao.observeTables(hallId)

    fun observeOrderItems(orderId: Long): Flow<List<OrderItem>> =
        dao.observeOrderItems(orderId)

    fun observeAllOrders(): Flow<List<RestaurantOrder>> =
        dao.observeAllOrders()

    suspend fun addHall() {
        database.withTransaction {
            dao.insertHall(
                Hall(hallNumber = dao.nextHallNumber())
            )
        }
    }

    suspend fun addTable(hallId: Int) {
        database.withTransaction {
            dao.insertTable(
                RestaurantTable(
                    hallId = hallId,
                    tableNumber = dao.nextTableNumber(hallId)
                )
            )
        }
    }

    suspend fun getOrCreateOpenOrder(
        hallId: Int,
        tableNumber: Int
    ): Long {
        return database.withTransaction {
            val table = dao.findTable(hallId, tableNumber)
                ?: error("Table not found")

            dao.findOpenOrder(table.id)?.id
                ?: dao.insertOrder(
                    RestaurantOrder(tableId = table.id)
                )
        }
    }

    suspend fun addItem(
        orderId: Long,
        menuItemId: Int,
        name: String,
        price: Int,
        quantity: Int
    ) {
        require(quantity > 0)

        database.withTransaction {
            dao.addItemToOrder(
                orderId = orderId,
                menuItemId = menuItemId,
                name = name,
                price = price,
                quantity = quantity
            )
        }
    }

    suspend fun closeOrder(orderId: Long) {
        database.withTransaction {
            dao.closeOrder(orderId)
        }
    }

    suspend fun initializeRestaurant() {
        database.withTransaction {
            if (dao.countHalls() == 0) {
                repeat(4) {
                    dao.insertHall(
                        Hall(
                            hallNumber = dao.nextHallNumber()
                        )
                    )
                }
            }

            val hallList: List<Hall> = dao.getHallsOnce()

            hallList.forEach { hall ->
                if (dao.countTablesForHall(hall.id) == 0) {
                    repeat(4) {
                        dao.insertTable(
                            RestaurantTable(
                                hallId = hall.id,
                                tableNumber = dao.nextTableNumber(hall.id)
                            )
                        )
                    }
                }
            }
        }
    }
}
