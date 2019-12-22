package com.reactivelabs.simpledatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class People(
    val firstName: String,
    val lastName: String,
    val age: Int,
    @PrimaryKey(autoGenerate = false) var id: String = UUID.randomUUID().toString()
)