package com.example.appw4

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnopenactivity = findViewById<Button>(R.id.OpenActivitybtn)
        btnopenactivity.setOnClickListener(View.OnClickListener{ view ->
            Util.Util.openActivity(this, PersonActivity::class.java)
        })

        val btnPeopleList= findViewById<Button>(R.id.btnPeopleList_main)
        btnPeopleList.setOnClickListener(View.OnClickListener{ view ->
            Util.Util.openActivity(this, PeopleListActivity::class.java)
        })
    }
}