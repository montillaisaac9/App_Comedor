package com.example.app_comedor.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.app_comedor.data.db.entity.DishEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface DishDao {

    @Query("SELECT * FROM dishes")
    suspend fun getAllDishes(): List<DishEntity>

    @Query("SELECT * FROM dishes WHERE id = :id")
    fun getDishById(id: Int): Flow<DishEntity?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDish(dish: DishEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDishes(dishes: List<DishEntity>): List<Long>

    @Update
    suspend fun updateDish(dish: DishEntity)

    @Delete
    suspend fun deleteDish(dish: DishEntity)

    @Query("DELETE FROM dishes")
    suspend fun deleteAllDishes()
}