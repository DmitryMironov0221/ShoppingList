package com.example.shoppinglist.domain

class AddShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    suspend fun addShoppingItem(shoppingItem : ShoppingItem){
        shoppingListRepository.addShoppingItem(shoppingItem)
    }

}