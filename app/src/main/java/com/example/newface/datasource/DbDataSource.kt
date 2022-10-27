package com.example.newface.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newface.model.User
import com.example.newface.model.UserDao

@Database(entities = [User::class], version = 1)
abstract class DbDataSource : RoomDatabase() {
    abstract fun userDao(): UserDao
}