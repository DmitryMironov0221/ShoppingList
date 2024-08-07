package com.example.shoppinglist.data

import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository

class ShoppingListRepositoryImpl(application: Application) : ShoppingListRepository {

    private val shoppingListDao = AppDataBase.getInstance(application).shoppingListDao()
    private val mapper = ShoppingListMapper()

    override suspend fun addShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListDao.addShoppingItem(mapper.mapEntityToDbModel(shoppingItem))
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListDao.deleteShoppingItem(shoppingItem.id)
    }

    override suspend fun editingShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListDao.addShoppingItem(mapper.mapEntityToDbModel(shoppingItem))
    }

    override suspend fun getShoppingItem(shoppingItemId: Int): ShoppingItem {
        val dbModel = shoppingListDao.getShopItem(shoppingItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShoppingList(): LiveData<List<ShoppingItem>> = MediatorLiveData<List<ShoppingItem>>().apply {
        addSource(shoppingListDao.getShoppingList()){
            value = mapper.mapListDbModelToListEntity(it)
        }
    }
    // у всех методов кроме getShoppingList() у которого возвращаемый тип LiveData нужно прописать suspend
// для того чтобы они работали в корутинах LiveData сама переключается на другой поток


}