package com.mut_jaeryo.saveaccount.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.Result
import com.mut_jaeryo.saveaccount.data.Result.Error
import com.mut_jaeryo.saveaccount.data.Result.Success
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dataSource: AccountDataSource
) : AccountRepository {

    private var cachedAccounts: LinkedHashMap<Int, Account> = LinkedHashMap()

    /**
     * Accounts 리스트를 cache, loacl ( Sqlite) 우선순위로 가져온다.
     */
    override suspend fun getAccounts(forceUpdate: Boolean): Result<List<Account>> {

        if (cachedAccounts.isEmpty() || forceUpdate) {
            loadAccountsFromLocalDataSource()
        }

        return dataSource.getAccounts()
    }

    /**
     * Accounts 객체를 cache, loacl ( Sqlite) 우선순위로 가져온다.
     */
    override suspend fun getAccount(accountId: Int): Result<Account> {
        val accountInCache = getTaskWithId(accountId)

        if (accountInCache == null) {
            loadAccountFromLocalDataSource(accountId)
        }

        return dataSource.getAccount(accountId)
    }

    /**
     * 추가되는 Account 객체를 cache List에도 추가하고 UI 변경 작업도 수행
     */
    override suspend fun saveAccount(account: Account) {
        coroutineScope {
            launch { dataSource.saveAccount(account) }
            launch { cacheAndPerform(account) }
        }
    }

    override suspend fun refreshAccounts() {
        cachedAccounts.clear()
        loadAccountsFromLocalDataSource()
    }

    override fun observeAccounts(): LiveData<Result<List<Account>>> {
        return dataSource.observeAccounts()
    }

    override suspend fun deleteAllAccounts() {
        coroutineScope {
            launch { dataSource.deleteAllAccounts() }
            launch { cachedAccounts.clear() }
        }
    }

    override suspend fun deleteAccount(accountId: Int) {
        coroutineScope {
            launch { dataSource.deleteAccount(accountId) }
            launch { cachedAccounts.remove(accountId) }
        }
    }

    private suspend fun loadAccountsFromLocalDataSource() {
        val resultAccounts = dataSource.getAccounts()

        if (resultAccounts is Success) {
            refreshCache(resultAccounts.data)
        } else if (resultAccounts is Error) {
            throw resultAccounts.exception
        }
    }

    private suspend fun loadAccountFromLocalDataSource(accountId: Int) {
        val resultAccount = dataSource.getAccount(accountId)

        if (resultAccount is Success) {
            cacheAndPerform(resultAccount.data)
        }
    }


    /**
     * cached List를 모두 버리고 새로운 정보를 저장
     */
    private suspend fun refreshCache(accounts: List<Account>) {
        coroutineScope {
            cachedAccounts.clear()
            accounts.forEach {
                cacheAndPerform(it)
            }
        }
    }

    /**
     * using primary key get Account from chached list
     */
    private fun getTaskWithId(id: Int) = cachedAccounts[id]

    /**
     * 변경되는 Account 객체를 cached하는 동시에 원하는 일도 수행
     */
    private suspend fun cacheAndPerform(account: Account) {
        val cacheAccount = Account(
            categoryId = account.categoryId,
            site = account.site,
            userId = account.userId,
            userPwd = account.userPwd,
            id = account.id
        )
        cachedAccounts.put(cacheAccount.id, cacheAccount)
    }
}