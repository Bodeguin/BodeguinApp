package pe.edu.upc.bodeguin.data.persistance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.upc.bodeguin.data.persistance.dao.UserDao
import pe.edu.upc.bodeguin.data.persistance.model.User

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "BodeguinDatabase")
                    .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}