package pe.edu.upc.bodeguin.data.persistance.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User (
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    @ColumnInfo(name = "correo")
    val correo: String?,
    @ColumnInfo(name ="password")
    val password: String?,
    @ColumnInfo(name ="nombre")
    val nombre:String?,
    @ColumnInfo(name ="apellidoPaterno")
    val apellidoPaterno:String?,
    @ColumnInfo(name ="apellidoMaterno")
    val apellidoMaterno:String?,
    @ColumnInfo(name ="direccion")
    val direccion: String?,
    @ColumnInfo(name ="dni")
    val dni: String?,
    @ColumnInfo(name ="enable")
    val enable: Boolean?,
    @ColumnInfo(name ="adm")
    val adm: Boolean?
)