package com.reactivelabs.simpledatabase.data

import android.util.Log
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

    fun getPeoples(option: String) = async {

        Log.i("Data", App.db.peopleDao().getAverageAge().toString())
        when(option) {

            "All" -> App.db.peopleDao().getAll()
            "Greater" -> App.db.peopleDao().getGreaterThan(20)
            "Adults" -> App.db.peopleDao().getAllAdults()
            "Range" -> App.db.peopleDao().getAgeBetween(20,26)
        else -> App.db.peopleDao().getAll()
        }
    }

}