package com.reactivelabs.simpledatabase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import com.reactivelabs.simpledatabase.R
import com.reactivelabs.simpledatabase.data.People
import kotlinx.android.synthetic.main.item_people.view.*

class PeoplesAdapter(
    private val peoples: MutableList<People>,
    private val actionDelete: (People) -> Unit,
    private val actionEdit: (People) -> Unit
) : RecyclerView.Adapter<PeoplesAdapter.PeopleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_people, parent, false)
        return PeopleHolder(view)
    }

    override fun getItemCount() = peoples.size

    override fun onBindViewHolder(holder: PeopleHolder, position: Int) {
        holder.bind(peoples[position], actionEdit) {
            actionDelete(it)
            peoples.remove(it)
            notifyDataSetChanged()
        }
    }

    class PeopleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(people: People,
                 actionEdit: (People) -> Unit,
                 actionDelete: (People) -> Unit) = itemView.apply {

            people.apply {
                nameFirst.text = firstName
                nameLast.text = lastName
                peopleAge.text = age.toString()
            }

            buttonDelete.setOnClickListener {
                actionDelete(people)
            }

            buttonEdit.setOnClickListener {
                actionEdit(people)
            }
        }
    }
}