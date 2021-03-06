package pe.edu.upc.bodeguin.data.persistance.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import pe.edu.upc.bodeguin.data.persistance.dao.CartDao
import pe.edu.upc.bodeguin.data.persistance.dao.UserDao
import pe.edu.upc.bodeguin.data.persistance.model.Cart
import pe.edu.upc.bodeguin.data.persistance.model.User

@Database(entities = [User::class, Cart::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        operator fun invoke(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "LocalDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}