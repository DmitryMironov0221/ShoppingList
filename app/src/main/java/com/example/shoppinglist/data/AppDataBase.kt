package com.example.shoppinglist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShoppingItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){

    abstract fun shoppingListDao(): ShoppingListDao

    companion object {

        private var INSTANCE: AppDataBase? = null// переменная для хранения единственного объекта бд
        private val LOCK = Any() // переменная для блокировки одновременного обращения к бд из разных потоков
        private const val DB_NAME = "shop_item.db"

        fun getInstance(application: Application) : AppDataBase{// метод для получения единственного объекта бд
            synchronized(LOCK){// блокировка для предотвращения одновременного обращения к бд из разных потоков
                INSTANCE?.let { // проверка на null для предотвращения повторного создания объекта бд
                    return it
                }
                val db = Room.databaseBuilder(// создание объекта бд
                    application,
                    AppDataBase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }

    }
}