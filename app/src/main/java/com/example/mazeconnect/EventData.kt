package com.example.mazeconnect

data class EventData(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val location: String = "",
    val ticketPrice: String = "",
    val category: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val organizerId: String? = null
)
