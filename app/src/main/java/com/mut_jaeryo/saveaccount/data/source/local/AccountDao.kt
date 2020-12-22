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
    @Query("SELECT * from accounts")
    suspend fun getAccounts(): List<Account>

    @Query("SELECT * from accounts")
    fun observeAccounts(): LiveData<List<Account>>

    /*
     * Select Account
     * @param accountId the account id
     * @return the account with id
     */
    @Query("SELECT * from accounts WHERE entryId = :accountId")
    suspend fun getAccountById(accountId: Int): Account?

    @Query("SELECT * from accounts WHERE entryId = :accountId")
    fun observeAccountById(accountId: Int): LiveData<Account>

    /**
     * 동일하 값이 있을 경우, 해당 값을 교체
     *
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account)

    /**
     * Delete Account by id
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM accounts WHERE entryId = :accountId")
    suspend fun deleteAccountById(accountId: Int): Int


    @Query("DELETE FROM accounts")
    suspend fun deleteAll()
}