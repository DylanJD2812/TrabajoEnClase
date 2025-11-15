package com.example.appw4

import Controller.PersonController
import Entity.Person
import Interface.OnItemClickListener
import Util.EXTRA_PERSON_ID
import Util.Util
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PeopleListActivity : AppCompatActivity(), OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_people_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recycler =  findViewById<RecyclerView>(R.id.rvPerson)
        val personController = PersonController(this)
        val customAdapter = PeopleListAdapter(personController.getPeople(), this)
        val layoutManager = LinearLayoutManager(applicationContext)
        recycler.layoutManager = layoutManager
        recycler.adapter = customAdapter
        customAdapter.notifyDataSetChanged()
    }


    override fun onItemClicked(person: Person) {
        Util.openActivity(this, PersonActivity::class.java,
            EXTRA_PERSON_ID,person.ID
            )
    }
}