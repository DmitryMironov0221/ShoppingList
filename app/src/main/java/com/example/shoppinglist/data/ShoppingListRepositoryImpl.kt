package com.example.shoppinglist.data

import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository

object ShoppingListRepositoryImpl : ShoppingListRepository{

    private val itemsLD = MutableLiveData<List<ShoppingItem>>()
    private val items = mutableListOf<ShoppingItem>()
    private var autoIncrementId = 0

    init {
        for (i in 0 until 10){
            val item = ShoppingItem("Name $i", i, true)
            addShoppingItem(item)
        }
    }

    private fun updateList(){
        itemsLD.value = items.toList()
    }

    override fun addShoppingItem(shoppingItem: ShoppingItem) {
        if (shoppingItem.id == ShoppingItem.UNDEFINED_ID){
            shoppingItem.id = autoIncrementId++
        }
        items.add(shoppingItem)
        updateList()

    }

    override fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        items.remove(shoppingItem)
        updateList()
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