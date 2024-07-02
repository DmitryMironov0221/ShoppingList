package com.example.shoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingListItemCallback : DiffUtil.ItemCallback<ShoppingItem>() {
    override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
        return oldItem == newItem
    }
}