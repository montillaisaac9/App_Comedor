package com.example.app_comedor.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_comedor.data.db.database.DataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val RoomModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            DataBase::class.java,
            "app_database"
        ).build()
    }
    single {
        get<DataBase>().getUserDao() // Provide UserDao
    }
    single {
        get<DataBase>().getMenuItemDao() // Provide MenuItemDao
    }
    single {
        get<DataBase>().getDishDao() // Provide DishDao
    }
}