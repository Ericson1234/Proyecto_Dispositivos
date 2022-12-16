package com.lugares_j.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class Libro(

    var id:String,
    val nombre : String,
    val categoria : String,
    val correo : String?, // ? el correo puede ser nulo en la base de datos
    val telefono : String?,

)   :Parcelable {
    constructor () :
                this("",
                    "",
                    "",
                    "","")
}
