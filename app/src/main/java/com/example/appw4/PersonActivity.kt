package com.example.appw4

import Controller.PersonController
import Entity.Person
import Entity.Province
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Date

class PersonActivity : AppCompatActivity() {
    private lateinit var txtId: EditText
    private lateinit var txtName: EditText
    private lateinit var txtFLastName: EditText
    private lateinit var txtSLastName: EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtBirthday: EditText
    private lateinit var txtProvince: EditText
    private lateinit var txtState: EditText
    private lateinit var txtDistrict: EditText
    private lateinit var txtAddress: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_person)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtId=findViewById<EditText>(R.id.idText)
        txtName=findViewById<EditText>(R.id.nameText)
        txtFLastName=findViewById<EditText>(R.id.fnameText)
        txtSLastName=findViewById<EditText>(R.id.snameText)
        txtPhone=findViewById<EditText>(R.id.phoneText)
        txtEmail=findViewById<EditText>(R.id.emailText)
        txtBirthday=findViewById<EditText>(R.id.bdateText)
        txtProvince=findViewById<EditText>(R.id.provinceText)
        txtState=findViewById<EditText>(R.id.stateText)
        txtDistrict=findViewById<EditText>(R.id.districtText)
        txtAddress=findViewById<EditText>(R.id.addressText)
        val btnSave =findViewById<Button>(R.id.savebtnps)
        btnSave.setOnClickListener(View.OnClickListener{view ->
            savePerson()
        })
    }

    fun validationData(person: Person): Boolean{
        return true
    }

    fun savePerson(){
        try {
            val person = Person()
            person.ID = txtId.text.toString()
            person.Name = txtName.text.toString()
            person.FLastName = txtFLastName.text.toString()
            person.SLastName = txtSLastName.text.toString()
            person.Phone = txtPhone.text.toString().toInt()
            person.Email = txtEmail.text.toString()
            //person.Birthday = txtBirthday.text.toString()
            //person.Province =txtProvince.text.toString()
            person.State = txtState.text.toString()
            person.District = txtDistrict.text.toString()
            person.Address = txtAddress.text.toString()
            if(validationData(person)){
                val personController = PersonController(this)
                personController.addPerson(person)
                Toast.makeText(this, "Se guardó", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Datos insuficientes", Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString(),Toast.LENGTH_LONG).show()
        }
    }
}