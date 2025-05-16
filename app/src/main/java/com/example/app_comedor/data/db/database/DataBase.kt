package com.example.app_comedor.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.app_comedor.data.db.converter.CareerTypeConverter
import com.example.app_comedor.data.db.dao.DishDao
import com.example.app_comedor.data.db.dao.MenuItemDao
import com.example.app_comedor.data.db.dao.UserDao
import com.example.app_comedor.data.db.entity.DishEntity
import com.example.app_comedor.data.db.entity.MenuItemEntity
import com.example.app_comedor.data.db.entity.UserEntity

@Database(entities = [UserEntity::class, MenuItemEntity::class, DishEntity::class], version = 2, exportSchema = false)
@TypeConverters(CareerTypeConverter::class)
abstract class DataBase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getMenuItemDao(): MenuItemDao
    abstract fun getDishDao(): DishDao

}