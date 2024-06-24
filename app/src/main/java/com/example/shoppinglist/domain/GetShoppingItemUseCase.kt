package com.example.shoppinglist.domain

class GetShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun getShoppingItem(shoppingItemId: Int): ShoppingItem{
        return shoppingListRepository.getShoppingItem(shoppingItemId)
    }
}