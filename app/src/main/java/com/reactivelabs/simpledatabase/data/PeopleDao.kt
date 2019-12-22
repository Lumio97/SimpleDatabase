package com.reactivelabs.simpledatabase.data

import androidx.room.*

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeople(people: People)

    @Delete
    fun deletePeople(people: People)

    @Query("SELECT * FROM people")
    fun getAll(): List<People>
}