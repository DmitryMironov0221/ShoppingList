package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShoppingListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShoppingItemUseCase
import com.example.shoppinglist.domain.EditingShoppingItemUseCase
import com.example.shoppinglist.domain.GetShoppingListUseCase
import com.example.shoppinglist.domain.ShoppingItem

class MainViewModel : ViewModel(){

    private val repository = ShoppingListRepositoryImpl

    private val getShoppingListUseCase = GetShoppingListUseCase(repository)
    private val deleteShoppingItemUseCase = DeleteShoppingItemUseCase(repository)
    private val editingShoppingItemUseCase = EditingShoppingItemUseCase(repository)

    val item = getShoppingListUseCase.getShoppingList()

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        deleteShoppingItemUseCase.deleteShoppingItem(shoppingItem)
    }

    fun changeEnableState(shoppingItem: ShoppingItem){
        val newItem = shoppingItem.copy(enabled = !shoppingItem.enabled)
        editingShoppingItemUseCase.editingShoppingItem(newItem)
    }

}