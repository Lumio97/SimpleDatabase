package com.reactivelabs.simpledatabase.data

import com.reactivelabs.simpledatabase.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

class SimpleRepo(
    override val coroutineContext: CoroutineContext = Dispatchers.IO
) : CoroutineScope {

    fun insertPeople(people: People) = async {
        val id = App.db.peopleDao().insertPeople(people)

    }

    fun deletePeople(people: People) = async {
        App.db.peopleDao().deletePeople(people)
    }

    fun getAllPeoples() = async {
        App.db.peopleDao().getAll()
    }

}