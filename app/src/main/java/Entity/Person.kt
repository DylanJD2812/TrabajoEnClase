package Entity

import android.graphics.Bitmap
import java.util.Date

class Person {
    private var id: String= ""
    private var name: String= ""
    private var fLastName: String=""
    private var sLastName: String=""
    private var phone: Int=0
    private var email: String=""
    private lateinit var birthday: Date
    private lateinit var province: Province
    private var state: String=""
    private var district: String=""
    private var address: String=""
    private var latitude: Int=0
    private var longitude: Int=0
    private var photo: Bitmap?

    constructor(id: String,name: String,fLastName: String,
                sLastName: String,phone: Int, email: String,
                birthday: Date, province: Province,state: String,
                district: String, address: String,
                latitude: Int, longitude: Int,photo: Bitmap?){
        this.id=id
        this.name=name
        this.fLastName=fLastName
        this.sLastName=sLastName
        this.phone=phone
        this.email=email
        this.birthday=birthday
        this.province=province
        this.state=state
        this.district=district
        this.address=address
        this.latitude=latitude
        this.longitude=longitude
        this.photo=photo
    }

    var ID: String
        get() = this.id
        set(value){this.id=value}

    var Name: String
        get() = this.name
        set(value){this.name=value}

    var FLastName: String
        get() = this.fLastName
        set(value){this.fLastName=value}

    var Phone: Int
        get() = this.phone
        set(value){this.phone=value}

    var Email: String
        get() = this.email
        set(value){this.email=value}

    var Birthday: Date
        get() = this.birthday
        set(value){this.birthday=value}

    var Province: Province
        get() = this.province
        set(value){this.province=value}

    var State: String
        get() = this.state
        set(value){this.state=value}

    var District: String
        get() = this.district
        set(value){this.district=value}

    var Address: String
        get() = this.address
        set(value){this.address=value}

    var Latitude: Int
        get() = this.latitude
        set(value){this.latitude=value}

    var Longitude: Int
        get() = this.longitude
        set(value){this.longitude=value}

    var Photo: Bitmap?
        get() = this.photo
        set(value){this.photo=value}

    fun FullName() = "$this.name $this.fLastName $this.sLastName"

}