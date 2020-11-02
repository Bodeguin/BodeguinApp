package pe.edu.upc.bodeguin.data.persistance.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Cart (
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    @ColumnInfo(name = "quantity")
    val quantity: Int?,
    @ColumnInfo(name ="price")
    val price: String?,
    @ColumnInfo(name ="priceMath")
    val priceMath: Double?,
    @ColumnInfo(name ="measureUnit")
    val measureUnit: String?,
    @ColumnInfo(name ="store")
    val store: String?,
    @ColumnInfo(name ="urlImageProduct")
    val urlImageProduct: String?,
    @ColumnInfo(name ="product")
    val product: String?
)