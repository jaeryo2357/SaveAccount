package com.mut_jaeryo.saveaccount.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mut_jaeryo.saveaccount.data.Account

/**
 * Data Access Object for the Account table
 */
@Dao
interface AccountDao {

    /**
     * Select all Account from the Account table
     */
    @Query("SELECT * from accounts") fun getAccounts(): LiveData<List<Account>>

    /**
     * 동일하 값이 있을 경우, 해당 값을 교체
     *
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertAccount(account: Account)


    /**
     * Update a Account
     * @param account account to be updated
     * @return the number of tasks updated, this should be always 1
     */
    @Update fun updateAccount(account: Account) : Int

    @Query("DELETE FROM accounts") fun deleteAll()
}