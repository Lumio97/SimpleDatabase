package com.reactivelabs.simpledatabase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        buttonAdd.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, EditFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        val repository = SimpleRepo()

        launch {
            val peoples = repository.getAllPeoples().await().toMutableList()
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
        }
    }
}