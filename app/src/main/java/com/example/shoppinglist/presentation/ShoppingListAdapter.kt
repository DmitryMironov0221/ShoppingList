package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingListAdapter: ListAdapter<ShoppingItem, ShoppingItemViewHolder>(ShoppingListItemCallback()) {

    var onShopItemLongClickListener: ((ShoppingItem) -> Unit)? = null
    var onShopItemClickListener: ((ShoppingItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val layout = when(viewType){
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return ShoppingItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = getItem(position)
        viewHolder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shoppingItem)
            true
        }
        viewHolder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shoppingItem)
        }
        viewHolder.tvName.text = shoppingItem.name
        viewHolder.tvCount.text = shoppingItem.count.toString()

    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled){
            VIEW_TYPE_ENABLED
        } else{
            VIEW_TYPE_DISABLED
        }
    }




    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 30
    }
}