package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShoppingListRepositoryImpl
import com.example.shoppinglist.domain.AddShoppingItemUseCase
import com.example.shoppinglist.domain.EditingShoppingItemUseCase
import com.example.shoppinglist.domain.GetShoppingItemUseCase
import com.example.shoppinglist.domain.ShoppingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ShoppingItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShoppingListRepositoryImpl(application)

    private val getShoppingItemUseCase = GetShoppingItemUseCase(repository)
    private val addShoppingItemUseCase = AddShoppingItemUseCase(repository)
    private val editShoppingItemUseCase = EditingShoppingItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName
    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount
    private val _shoppingItem = MutableLiveData<ShoppingItem>()
    val shoppingItem: LiveData<ShoppingItem>
        get() = _shoppingItem
    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getShoppingItem(shoppingItemId: Int) {
        viewModelScope.launch {// viewModelScope.launch запускает корутину, которая будет выполняться в фоновом потоке
            val item = getShoppingItemUseCase.getShoppingItem(shoppingItemId)
            _shoppingItem.value = item
        }

    }

    fun addShoppingItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        viewModelScope.launch {// viewModelScope.launch запускает корутину, которая будет выполняться в фоновом потоке
            if (fieldsValid) {
                val shoppingItem = ShoppingItem(name, count, true)
                addShoppingItemUseCase.addShoppingItem(shoppingItem)
                finishWork()
            }
        }


    }

    fun editShoppingItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        viewModelScope.launch {// viewModelScope.launch запускает корутину, которая будет выполняться в фоновом потоке
            if (fieldsValid) {
                _shoppingItem.value?.let {
                    val item = it.copy(name = name, count = count)
                    editShoppingItemUseCase.editingShoppingItem(item)
                    finishWork()
                }

            }
        }

    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: "" // эта запись означает, что если inputName не равно null то
        // обрезаем пробелы, а если там null, то ставим пустую строку
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }


    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}