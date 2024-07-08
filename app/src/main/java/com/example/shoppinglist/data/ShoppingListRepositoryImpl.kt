package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository

object ShoppingListRepositoryImpl : ShoppingListRepository {
    private val shopListLD = MutableLiveData<List<ShoppingItem>>()
    private val shopList = sortedSetOf<ShoppingItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val item = ShoppingItem("Name $i", i, true)
            addShoppingItem(item)
        }
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }

    override fun addShoppingItem(shopItem: ShoppingItem) {
        if (shopItem.id == ShoppingItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShoppingItem(shopItem: ShoppingItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editingShoppingItem(shoppingItem: ShoppingItem) {
        val oldElement = getShoppingItem(shoppingItem.id)
        shopList.remove(oldElement)
        addShoppingItem(shoppingItem)
    }

    override fun getShoppingItem(shopItemId: Int): ShoppingItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShoppingList(): LiveData<List<ShoppingItem>> {
        return shopListLD
    }
}