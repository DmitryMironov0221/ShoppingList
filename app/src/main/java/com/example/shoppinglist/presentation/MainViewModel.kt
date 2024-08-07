package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShoppingListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShoppingItemUseCase
import com.example.shoppinglist.domain.EditingShoppingItemUseCase
import com.example.shoppinglist.domain.GetShoppingListUseCase
import com.example.shoppinglist.domain.ShoppingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application){

    private val repository = ShoppingListRepositoryImpl(application)

    private val getShoppingListUseCase = GetShoppingListUseCase(repository)
    private val deleteShoppingItemUseCase = DeleteShoppingItemUseCase(repository)
    private val editingShoppingItemUseCase = EditingShoppingItemUseCase(repository)

    val item = getShoppingListUseCase.getShoppingList()

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {// viewModelScope.launch запускает корутину, которая будет выполняться в фоновом потоке
            deleteShoppingItemUseCase.deleteShoppingItem(shoppingItem)
        }

    }

    fun changeEnableState(shoppingItem: ShoppingItem){
        viewModelScope.launch {// viewModelScope.launch запускает корутину, которая будет выполняться в фоновом потоке
            val newItem = shoppingItem.copy(enabled = !shoppingItem.enabled)
            editingShoppingItemUseCase.editingShoppingItem(newItem)
        }

    }

}