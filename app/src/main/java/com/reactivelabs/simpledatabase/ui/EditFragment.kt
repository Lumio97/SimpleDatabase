package com.reactivelabs.simpledatabase.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.reactivelabs.simpledatabase.R
import com.reactivelabs.simpledatabase.data.People
import com.reactivelabs.simpledatabase.data.SimpleRepo
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class EditFragment(
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : Fragment(), CoroutineScope {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editFirstName.text = SpannableStringBuilder(arguments?.getString("firstName") ?: "")
        editLastName.text = SpannableStringBuilder(arguments?.getString("lastName") ?: "")
        editAge.text = SpannableStringBuilder(arguments?.getInt("age")?.toString() ?: "")

        buttonDone.setOnClickListener {
            launch {
                val people = People(
                    firstName = editFirstName.text.toString(),
                    lastName = editLastName.text.toString(),
                    age = editAge.text.toString().toInt(),
                    id = arguments?.getString("id") ?: UUID.randomUUID().toString()
                )

                SimpleRepo().insertPeople(people).await()

                fragmentManager?.beginTransaction()
                    ?.replace(R.id.container, MainFragment())
                    ?.commit()
            }
        }
    }
}