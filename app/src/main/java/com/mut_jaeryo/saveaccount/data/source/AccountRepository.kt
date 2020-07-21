package com.mut_jaeryo.saveaccount.data.source

import android.content.Context
import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.source.local.AccountLocalDataSource

class AccountRepository(
    val dataSource: AccountDataSource
) : AccountDataSource {

    var cachedAccounts: LinkedHashMap<String, Account> = LinkedHashMap()


    /**
     * Accounts 리스트를 cache, loacl ( Sqlite) 우선순위로 가져온다.
     */
    override fun getAccounts(callback: AccountDataSource.LoadAccountsCallback) {

        if (cachedAccounts.isNotEmpty()) {      //이전에 로드한 상황이라면
            callback.onAccountsLoaded(ArrayList(cachedAccounts.values))
            return
        }

        dataSource.getAccounts(object : AccountDataSource.LoadAccountsCallback {
            override fun onAccountsLoaded(accounts: List<Account>) {
                refreshCache(accounts)
                callback.onAccountsLoaded(accounts)
            }

            override fun onDataNotAvailable() {
                //Google MVP에서는 네트워크에서 다운
                callback.onDataNotAvailable()
            }
        })
    }


    /**
     * Accounts 객체를 cache, loacl ( Sqlite) 우선순위로 가져온다.
     */

    override fun getAccount(accountId: String, callback: AccountDataSource.GetAccountsCallback) {
        val accountInCache = getTaskWithId(accountId)

        if (accountInCache != null) {
            callback.onAccountLoaded(accountInCache)
            return
        }

        dataSource.getAccount(accountId, object : AccountDataSource.GetAccountsCallback {
            override fun onAccountLoaded(account: Account) {
                cacheAndPerform(account) {
                    callback.onAccountLoaded(account)
                }
            }

            override fun onDataNotAvailable() {
               callback.onDataNotAvailable()
            }

        })
    }

    /**
     * 추가되는 Account 객체를 cache List에도 추가하고 UI 변경 작업도 수행
     */
    override fun saveAccount(account: Account) {
        cacheAndPerform(account) {
            dataSource.saveAccount(it)
        }
    }

    override fun deleteAllAccounts() {
        dataSource.deleteAllAccounts()
        cachedAccounts.clear()
    }

    override fun deleteAccount(accountId: String) {
        dataSource.deleteAccount(accountId)
        cachedAccounts.remove(accountId)
    }

    /**
     * cached List를 모두 버리고 새로운 정보를 저장
     */
    private fun refreshCache(accounts : List<Account>) {
        cachedAccounts.clear()
        accounts.forEach {
            cacheAndPerform(it) {}
        }
    }

    /**
     * using primary key get Account from chached list
     */
    private fun getTaskWithId(id: String) = cachedAccounts[id]

    /**
     * 변경되는 Account 객체를 cached하는 동시에 원하는 일도 수행
     */
    private inline fun cacheAndPerform(account : Account, perform : (Account) -> Unit) {
        val cacheAccount = Account(account.site, account.userId, account.userPwd, account.id)
        cachedAccounts.put(cacheAccount.id, cacheAccount)
        perform(cacheAccount)
    }

    companion object {
        private var Instance: AccountRepository? = null

        @JvmStatic
        fun getInstance(
            dataSource: AccountLocalDataSource
        ): AccountRepository {
            return Instance ?: AccountRepository(dataSource).apply {
                Instance = this
            }
        }

        @JvmStatic
        fun getInstance(context: Context): AccountRepository {
            val dataSource = AccountLocalDataSource.getInstance(context)
            return Instance ?: AccountRepository(dataSource).apply {
                Instance = this
            }
        }
        @JvmStatic
        fun destroyInstance() {
            Instance = null
        }
    }
}