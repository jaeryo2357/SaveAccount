package com.mut_jaeryo.saveaccount.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.Result
import com.mut_jaeryo.saveaccount.data.Result.Success
import com.mut_jaeryo.saveaccount.data.Result.Error
import com.mut_jaeryo.saveaccount.data.source.AccountDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class LocalAccountDataSource @Inject constructor(
    private val accountDao: AccountDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AccountDataSource {

    override suspend fun getAccounts(): Result<List<Account>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(accountDao.getAccounts())
        } catch (e: Exception) {
            Error(e)
        }
    }


    override suspend fun getAccount(accountId: Int) = withContext(ioDispatcher) {
        try {
            val account = accountDao.getAccountById(accountId)
            if (account != null) {
                return@withContext Success(account)
            } else {
                return@withContext Error(Exception("Account not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override fun observeAccounts(): LiveData<Result<List<Account>>> {
       return accountDao.observeAccounts().map {
           Success(it)
       }
    }

    override fun observeAccount(accountId: Int): LiveData<Result<Account>> {
        return accountDao.observeAccountById(accountId).map {
            Success(it)
        }
    }

    override suspend fun saveAccount(account: Account) = withContext(ioDispatcher) {
        accountDao.insertAccount(account)
    }

    override suspend fun deleteAllAccounts() = withContext(ioDispatcher) {
        accountDao.deleteAll()
    }

    override suspend fun deleteAccount(accountId: Int) = withContext<Unit>(ioDispatcher) {
        accountDao.deleteAccountById(accountId)
    }
}