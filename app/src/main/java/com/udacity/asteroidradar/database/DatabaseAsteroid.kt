package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities=[DataBaseEntity::class], version = 1, exportSchema = false)
abstract class DatabaseAsteroid : RoomDatabase(){
    abstract val dataBaseDao : DataBaseDao
}

private lateinit var INSTANCE : DatabaseAsteroid

fun getDatabase(context: Context): DatabaseAsteroid{
    synchronized(DatabaseAsteroid::class.java){
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                DatabaseAsteroid::class.java, "asteroids")
                .build()
        }
    }
    return INSTANCE
}