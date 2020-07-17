package com.mut_jaeryo.saveaccount.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mut_jaeryo.saveaccount.data.Account

@Database(entities = arrayOf(Account::class), version = 1, exportSchema = false)
public abstract class AccountDataBase : RoomDatabase(){

    abstract fun accountDao(): AccountDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AccountDataBase? = null

        fun getDatabase(
            context: Context
        ): AccountDataBase {
            return INSTANCE
                ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccountDataBase::class.java,
                    "accounts.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}