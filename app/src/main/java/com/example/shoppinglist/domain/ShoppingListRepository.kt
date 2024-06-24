package com.example.shoppinglist.domain

interface ShoppingListRepository {

    fun addShoppingItem(shoppingItem : ShoppingItem)

    fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun editingShoppingItem(shoppingItem: ShoppingItem)

    fun getShoppingItem(shoppingItemId: Int): ShoppingItem

    fun getShoppingList(): List<ShoppingItem>
}