package com.example.shoppinglist.domain

class EditingShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun editingShoppingItem(shoppingItem: ShoppingItem){
        shoppingListRepository.editingShoppingItem(shoppingItem)
    }


}