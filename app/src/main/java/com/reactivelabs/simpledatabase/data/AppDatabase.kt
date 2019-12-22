package com.reactivelabs.simpledatabase.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [People::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
}