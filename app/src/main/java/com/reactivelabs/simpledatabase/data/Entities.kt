package com.reactivelabs.simpledatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class People(
    val firstName: String,
    val lastName: String,
    val age: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)