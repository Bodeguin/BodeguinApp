package pe.edu.upc.bodeguin.data.persistance.dao

import android.text.style.TtsSpan
import androidx.lifecycle.LiveData
import androidx.room.*
import pe.edu.upc.bodeguin.data.persistance.model.Cart

@Dao
interface CartDao {
    @Query("select IfNull(sum(priceMath),0) from Cart")
    fun getTotalPrice(): LiveData<Double>

    @Query("select * from Cart order by product")
    fun getShoppingCart(): LiveData<List<Cart>>

    @Query("select * from Cart order by product")
    fun getCarts(): List<Cart>

    @Query("select Count(*) from Cart")
    fun getTotalItemsCarts(): Int

    @Query("select * from Cart where id = :id")
    fun getById(id: Int): Cart

    @Insert(onConflict =  OnConflictStrategy.REPLACE )
    suspend fun insert(vararg cart: Cart)

    @Delete
    fun delete(vararg cart: Cart)

    @Query("delete from Cart")
    fun deleteAll()
}
