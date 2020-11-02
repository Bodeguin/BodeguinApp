package pe.edu.upc.bodeguin.data.persistance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.upc.bodeguin.data.persistance.dao.CartDao
import pe.edu.upc.bodeguin.data.persistance.dao.UserDao
import pe.edu.upc.bodeguin.data.persistance.model.Cart
import pe.edu.upc.bodeguin.data.persistance.model.User

@Database(entities = [User::class, Cart::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "BodeguinDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}