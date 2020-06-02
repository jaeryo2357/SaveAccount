package com.mut_jaeryo.saveaccount.account.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mut_jaeryo.saveaccount.account.model.Account
import com.mut_jaeryo.saveaccount.account.model.AccountDao
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Account::class), version = 1, exportSchema = false)
public abstract class AccountDB : RoomDatabase(){

    abstract fun accountDao(): AccountDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AccountDB? = null

        fun getDatabase(
            context: Context
        ): AccountDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccountDB::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}