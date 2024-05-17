package com.example.servisilab.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.servisilab.data.daos.LogDao
import com.example.servisilab.data.entities.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Log::class], version = 1, exportSchema = false)
abstract class LogDatabase : RoomDatabase() {
    abstract fun logDao() : LogDao

    companion object {
        @Volatile
        private var INSTANCE: LogDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): LogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LogDatabase::class.java,
                    "user_database"
                ).addCallback(LogDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class LogDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    clearDatabase(database.logDao())
                }
            }
        }

        suspend fun clearDatabase(dao: LogDao) {
            dao.deleteAll()
//
//            dao.insert(Log(0, "", "this"))
//            val l = dao.getLog("this")
//            dao.delete(l)
        }
    }
}