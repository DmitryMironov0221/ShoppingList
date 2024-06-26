package com.example.shoppinglist.presentation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingListAdapter: RecyclerView.Adapter<ShoppingListAdapter.ShoppingItemViewHolder>() {

    var count = 0
    var shoppingList = listOf<ShoppingItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onShopItemLongClickListener: ((ShoppingItem) -> Unit)? = null
    var onShopItemClickListener: ((ShoppingItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        Log.d("ShoppingListAdapter", "onCreateViewHolder, count: ${++count}")
        val layout = when(viewType){
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return ShoppingItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = shoppingList[position]
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

    override fun onViewRecycled(viewHolder: ShoppingItemViewHolder) {
        super.onViewRecycled(viewHolder)
        viewHolder.tvName.text = ""
        viewHolder.tvCount.text = ""
        viewHolder.tvName.setTextColor(
            ContextCompat.getColor(
                viewHolder.itemView.context,
                android.R.color.white
            )
        )
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = shoppingList[position]
        return if (item.enabled){
            VIEW_TYPE_ENABLED
        } else{
            VIEW_TYPE_DISABLED
        }
    }

    class ShoppingItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)

    }


    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 30
    }
}