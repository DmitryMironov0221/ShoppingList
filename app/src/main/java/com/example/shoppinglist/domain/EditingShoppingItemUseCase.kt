package com.example.shoppinglist.domain

class EditingShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    suspend fun editingShoppingItem(shoppingItem: ShoppingItem){
        shoppingListRepository.editingShoppingItem(shoppingItem)
    }


}