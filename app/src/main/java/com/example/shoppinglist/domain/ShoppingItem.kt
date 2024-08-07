package com.example.shoppinglist.domain

data class ShoppingItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
){
    companion object{
        var UNDEFINED_ID = 0
    }
}
