package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShoppingListRepository {

    fun addShoppingItem(shoppingItem : ShoppingItem)

    fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun editingShoppingItem(shoppingItem: ShoppingItem)

    fun getShoppingItem(shoppingItemId: Int): ShoppingItem

    fun getShoppingList(): LiveData<List<ShoppingItem>>
}