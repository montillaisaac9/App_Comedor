package com.example.app_comedor.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.app_comedor.data.db.entity.MenuItemEntity
import com.example.app_comedor.data.db.entity.MenuItemWithDish

@Dao
interface MenuItemDao {
    @Transaction
    @Query("SELECT * FROM menu_items")
    suspend fun getAllMenuItems(): List<MenuItemWithDish>

    @Transaction
    @Query("SELECT * FROM menu_items WHERE id = :id")
    suspend fun getMenuItemById(id: Int): MenuItemWithDish?

    @Transaction
    @Query("SELECT * FROM menu_items WHERE date = :date")
    suspend fun getMenuItemsByDate(date: String): List<MenuItemWithDish>

    @Transaction
    @Query("SELECT * FROM menu_items WHERE weekDay = :weekDay")
    suspend fun getMenuItemsByWeekDay(weekDay: String): List<MenuItemWithDish>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItem(menuItem: MenuItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItems(menuItems: List<MenuItemEntity>): List<Long>

    @Update
    suspend fun updateMenuItem(menuItem: MenuItemEntity)

    @Delete
    suspend fun deleteMenuItem(menuItem: MenuItemEntity)

    @Query("DELETE FROM menu_items")
    suspend fun deleteAllMenuItems()
}