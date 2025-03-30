package com.example.app_comedor.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import com.example.app_comedor.data.db.converter.CareerTypeConverter
import com.example.app_comedor.data.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(CareerTypeConverter::class)
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("select * FROM users")
    fun getUserFlow(): Flow<UserEntity>

    @Query("DELETE FROM users")
    suspend fun deleteUser()

    @Update
    suspend fun updateUser(user: UserEntity)

}