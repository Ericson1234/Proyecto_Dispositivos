package com.lugares_j.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class Lugar(

    var id:String,
    val nombre : String,
    val correo : String?, // ? el correo puede ser nulo en la base de datos
    val telefono : String?,
    val web : String?,
    val latitud : Double?,
    val longitud : Double?,
    val altura : Double?,
    val ruta_audio : String?,
    val ruta_imagen : String?
)   :Parcelable {
    constructor () :
                this("",
                    "",
                    "",
                    "","",0.0,0.0,0.0,"","")
}
