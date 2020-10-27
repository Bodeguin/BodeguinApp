package pe.edu.upc.bodeguin.data.persistance.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User (
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name ="password")
    val password: String?,
    @ColumnInfo(name ="name")
    val name:String?,
    @ColumnInfo(name ="firstLastName")
    val firstLastName:String?,
    @ColumnInfo(name ="secondLastName")
    val secondLastName:String?,
    @ColumnInfo(name ="direction")
    val direction: String?,
    @ColumnInfo(name ="dni")
    val dni: String?
)