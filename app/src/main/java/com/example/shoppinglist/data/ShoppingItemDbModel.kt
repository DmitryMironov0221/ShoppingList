package com.example.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_items")
data class ShoppingItemDbModel(
    @PrimaryKey(autoGenerate = true)//автоматически генерирует id
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean
)