package com.example.appw4

import Controller.PersonController
import Entity.Person
import Entity.Province
import Util.EXTRA_PERSON_ID
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.util.Calendar

class PersonActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var txtId: EditText
    private lateinit var txtName: EditText
    private lateinit var txtFLastName: EditText
    private lateinit var txtSLastName: EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtEmail: EditText
    private lateinit var lbBirthdate: TextView
    private lateinit var txtProvince: EditText
    private lateinit var txtState: EditText
    private lateinit var txtDistrict: EditText
    private lateinit var txtAddress: EditText

    private  var day :Int=0
    private  var month: Int=0
    private  var year: Int =0

    private var isEditMode: Boolean = false
    private lateinit var personController: PersonController

    private lateinit var menuItemDelete: MenuItem

    private lateinit var imgPhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_person)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        personController = PersonController(this)
        txtId=findViewById<EditText>(R.id.idText)
        txtName=findViewById<EditText>(R.id.nameText)
        txtFLastName=findViewById<EditText>(R.id.fnameText)
        txtSLastName=findViewById<EditText>(R.id.snameText)
        txtPhone=findViewById<EditText>(R.id.phoneText)
        txtEmail=findViewById<EditText>(R.id.emailText)
        lbBirthdate=findViewById<TextView>(R.id.lbBirthdate_Person)
        txtProvince=findViewById<EditText>(R.id.provinceText)
        txtState=findViewById<EditText>(R.id.stateText)
        txtDistrict=findViewById<EditText>(R.id.districtText)
        txtAddress=findViewById<EditText>(R.id.addressText)
        imgPhoto=findViewById<ImageView>(R.id.imgPhoto)

        ResetDate ()

        val personId = intent.getStringExtra(EXTRA_PERSON_ID)
        if(personId != null && personId.trim().length > 0) searchPerson(personId)

        val btnSelectDate = findViewById<ImageButton>(R.id.btnSelectDate_person)
        btnSelectDate.setOnClickListener(View.OnClickListener{view ->
            showDatePickerDialog()
        })

        val SelectPicture = findViewById<ImageButton>(R.id.btnSelectPicture)
        SelectPicture.setOnClickListener(View.OnClickListener{view ->
            takePhoto()
        })

    }

    private fun ResetDate (){
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)

    }
    private  fun searchPerson(id: String){
        try {
            val person = personController.GetById(id.trim().toString())
            if (person !=null){
                isEditMode=true
                txtId.isEnabled=false
                txtId.setText(id)
                txtName.setText(person.Name)
                txtFLastName.setText(person.FLastName)
                txtSLastName.setText(person.SLastName)
                txtEmail.setText(person.Email)
                txtPhone.setText(person.Phone.toString())
                lbBirthdate.setText(getDateString(person.Birthday.dayOfMonth
                    , person.Birthday.month.value, person.Birthday.year ))
                txtProvince.setText(person.Province.Name)
                txtState.setText(person.State)
                txtDistrict.setText(person.District)
                txtAddress.setText(person.Address)
                year = person.Birthday.year
                month = person.Birthday.month.value - 1
                day = person.Birthday.dayOfMonth
                //menuItemDelete.isVisible = isEditMode
                imgPhoto.setImageBitmap(person.Photo)
            }else{
                Toast.makeText(this, R.string.MsgDataNotFound, Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }
    private fun cleanScreen(){
        isEditMode=false
        ResetDate()
        txtId.isEnabled=true
        txtId.setText("")
        txtName.setText("")
        txtFLastName.setText("")
        txtSLastName.setText("")
        txtEmail.setText("")
        txtPhone.setText("")
        txtProvince.setText("")
        txtState.setText("")
        txtDistrict.setText("")
        txtAddress.setText("")
        lbBirthdate.setText("")
        invalidateOptionsMenu()
        imgPhoto.setImageBitmap(null)
    }
    fun isValidationData(): Boolean{
        val dateparse = Util.Util.parseStringToDateModern(lbBirthdate.text.toString(), "dd/MM/yyyy")
        return txtId.text.trim().isNotEmpty() && txtName.text.trim().isNotEmpty()
                && txtFLastName.text.trim().isNotEmpty() && txtSLastName.text.trim().isNotEmpty()
                && txtEmail.text.trim().isNotEmpty() && lbBirthdate.text.trim().isNotEmpty()
                && txtProvince.text.trim().isNotEmpty() && txtState.text.trim().isNotEmpty()
                && txtDistrict.text.trim().isNotEmpty() && txtAddress.text.trim().isNotEmpty()
                && (txtPhone.text.trim().isNotEmpty() && txtPhone.text.trim().length >= 8
                && txtPhone.text.toString()?.toInt()!! != null && txtPhone.text.toString()?.toInt()!! != 0)
                && dateparse != null


    }
    private fun showDatePickerDialog(){
        val datePickerDialog = DatePickerDialog(this,this,year,month,day)
        datePickerDialog.show()

    }
    private fun getDateString(dayValue: Int, monthValue: Int, yearValue: Int): String{
        return "${if(dayValue < 10) "0" else ""}$dayValue/${if(monthValue < 10) "0" else ""}$monthValue/$yearValue"
    }

    override fun onDateSet(
        view: DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        lbBirthdate.text=getDateString(dayOfMonth,month+1,year)
    }



    fun DeletePerson(){
        try{
            personController.removePerson(txtId.text.trim().toString())
            cleanScreen()
            Toast.makeText(this, R.string.MsgDeleteSuccess, Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun savePerson(){
        try {
            if (isValidationData()){
                if (personController.GetById(txtId.text.toString().trim()) != null
                    && !isEditMode){
                    Toast.makeText(this, getString(R.string.MsgDuplicateDate)
                        , Toast.LENGTH_LONG).show()
                }else{
                    val person = Person()
                    person.ID = txtId.text.toString()
                    person.Name = txtName.text.toString()
                    person.FLastName = txtFLastName.text.toString()
                    person.SLastName = txtSLastName.text.toString()
                    person.Email = txtEmail.text.toString()
                    person.Phone = txtPhone.text.toString().toInt()
                    val bDateParse = Util.Util.parseStringToDateModern(lbBirthdate.text.toString(),
                        "dd/MM/yyyy")
                    person.Birthday = LocalDate.of(bDateParse?.year!!, bDateParse.month.value
                        , bDateParse?.dayOfMonth!!)
                    val province = Province()
                    province.Name= txtProvince.text.toString()
                    person.Province = province
                    person.State = txtState.text.toString()
                    person.District = txtDistrict.text.toString()
                    person.Address= txtAddress.text.toString()
                    person.Photo = (imgPhoto.drawable as BitmapDrawable).bitmap

                    if (!isEditMode)
                        personController.addPerson(person)
                    else
                        personController.updatePerson(person)

                    cleanScreen()

                    Toast.makeText(this, getString(R.string.MsgSaveSuccess)
                        , Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Datos incompletos"
                    , Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.mnu_crud, menu)
        menuItemDelete= menu!!.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = isEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mnu_save ->{
                if (isEditMode){
                    Util.Util.showDialogCondition(this
                        , getString(R.string.TextSaveQuestion)
                        , { savePerson() })
                }else{
                    savePerson()
                }
                return true
            }
            R.id.mnu_delete ->{
                Util.Util.showDialogCondition(this
                    , getString(R.string.TextDeleteQuestion)
                    , { DeletePerson() })
                return true
            }
            R.id.mnu_cancel ->{
                cleanScreen()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Here start the methods to select a phone from the camera or gallery
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
        if (success) {
            // Image captured successfully, handle the result (e.g., display it in an ImageView)
            // The image is typically saved to the URI provided in the launch() call.
        } else {
            // Image capture failed or was cancelled
        }
    }

    private val cameraPreviewLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            imgPhoto.setImageBitmap(bitmap)
        } else {
            // Image capture failed or was cancelled
        }
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // Handle the selected image URI here
            data?.data?.let { imageUri ->
                imgPhoto.setImageURI(imageUri)
            }
        }
    }

    fun takePhoto(){
        cameraPreviewLauncher.launch(null)
    }

    fun selectPhoto(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
        selectImageLauncher.launch(intent)
    }


}

