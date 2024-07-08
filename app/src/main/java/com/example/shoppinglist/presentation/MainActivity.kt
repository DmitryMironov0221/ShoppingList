package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shoppingListAdapter: ShoppingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.item.observe(this) {
            shoppingListAdapter.submitList(it)
        }
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            val intent = ShoppingItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val rvShoppingList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvShoppingList) {
            shoppingListAdapter = ShoppingListAdapter()
            adapter = shoppingListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShoppingListAdapter.VIEW_TYPE_ENABLED,
                ShoppingListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShoppingListAdapter.VIEW_TYPE_DISABLED,
                ShoppingListAdapter.MAX_POOL_SIZE
            )
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvShoppingList)

    }

    private fun setupSwipeListener(rvShoppingList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shoppingListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShoppingItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShoppingList)
    }

    private fun setupClickListener() {
        shoppingListAdapter.onShopItemClickListener = {
            Log.d("MainActivity", it.toString())
            val intent = ShoppingItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)

        }
    }

    private fun setupLongClickListener() {
        shoppingListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }


}