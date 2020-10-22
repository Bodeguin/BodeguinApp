package pe.edu.upc.bodeguin.data.persistance.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.edu.upc.bodeguin.data.persistance.model.User

@Dao
interface UserDao {
    @Query("select * from User limit 1")
    fun getUser(): LiveData<User>

    @Query("select * from User limit 1")
    fun getUserEdit(): User

    @Insert(onConflict =  OnConflictStrategy.REPLACE )
    suspend fun insert(vararg user: User)

    @Query("delete from user")
    fun deleteAll()
}