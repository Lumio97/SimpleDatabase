package com.reactivelabs.simpledatabase.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.reactivelabs.simpledatabase.R
import com.reactivelabs.simpledatabase.data.SimpleRepo
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainFragment(
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : Fragment(), CoroutineScope {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val options = arrayOf("All","Adults","GreaterThan","Range")
        val dropDownAdapter = ArrayAdapter(context!!,android.R.layout.simple_spinner_dropdown_item,options)
        mode.adapter = dropDownAdapter

        buttonAdd.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, EditFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        val repository = SimpleRepo()

        launch {
            val peoples = repository.getPeoples(options[0]).await().toMutableList()
            val adapter = PeoplesAdapter(peoples, { people ->
                val bundle = Bundle()
                bundle.let {
                    it.putString("firstName", people.firstName)
                    it.putString("lastName", people.lastName)
                    it.putInt("age", people.age)
                    it.putString("id", people.id)
                }
                val editFragment = EditFragment()
                editFragment.arguments = bundle
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.container, editFragment)
                    ?.addToBackStack(null)
                    ?.commit()

            }, { repository.deletePeople(it) })
            peopleList.adapter = adapter

        mode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ){
               this@MainFragment.launch {
                   val newPeoples = repository.getPeoples(options[p2]).await()
                   Log.i("Debug", newPeoples.size.toString())
                   adapter.updatePeoplesList(newPeoples)

                }
            }

        }
        }
    }
}