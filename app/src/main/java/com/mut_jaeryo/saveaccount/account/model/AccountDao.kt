package com.mut_jaeryo.saveaccount.account.model

import androidx.lifecycle.LiveData
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
interface AccountDao {

    @Query("SELECT * from account_table")
    fun getAlphabetizedWords(): LiveData<List<Account>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(account: Account)

    @Query("DELETE FROM account_table")
    suspend fun deleteAll()
}