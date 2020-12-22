package com.mut_jaeryo.saveaccount.data.source

import androidx.lifecycle.LiveData
import com.mut_jaeryo.saveaccount.data.Result
import com.mut_jaeryo.saveaccount.data.Account

/**
 * Account Data를 접근하는 방법을 기술하는 interface
 */
interface AccountRepository {

    suspend fun getAccounts(forceUpdate: Boolean): Result<List<Account>>

    suspend fun getAccount(accountId: Int): Result<Account>

    suspend fun refreshAccounts()

    fun observeAccounts(): LiveData<Result<List<Account>>>

    suspend fun saveAccount(account: Account)

    suspend fun deleteAllAccounts()

    suspend fun deleteAccount(accountId: Int)
}