package com.example.shoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.domain.ShoppingItem
/* Этот класс можно использовать как часть одной из реализаций для обработки изменений в списке
элементов, для её использования необходимо внести изменения в adapter и mainActivity
но он не очень хороший, так как все действия выполняются в главном потоке, лучше использовать класс
ShoppingListItemCallback*/
class ShoppingListDiffCallback(
    private val oldList : List<ShoppingItem>,
    private val newList : List<ShoppingItem>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}