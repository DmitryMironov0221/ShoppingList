package com.example.shoppinglist.domain

data class ShoppingItem(
    val count: Int,
    val enabled: Boolean,
    val name: String,
    var id: Int = UNDEFINED_ID
){
    companion object{
        var UNDEFINED_ID = -1
    }
}
