package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository

object ShoppingListRepositoryImpl : ShoppingListRepository{

    private val items = mutableListOf<ShoppingItem>()
    private var autoIncrementId = 0

    override fun addShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItem.id = autoIncrementId++
        items.add(shoppingItem)
    }

    override fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        items.remove(shoppingItem)
    }

    override fun editingShoppingItem(shoppingItem: ShoppingItem) {
        val oldElement = getShoppingItem(shoppingItem.id)
        items.remove(oldElement)
        addShoppingItem(shoppingItem)
    }

    override fun getShoppingItem(shoppingItemId: Int): ShoppingItem {
        return items.find {
            it.id == shoppingItemId
        } ?: throw RuntimeException("Element not found")
    }

    override fun getShoppingList(): List<ShoppingItem> {
        return items.toList()
    }
}