package com.mut_jaeryo.saveaccount.data.source

import com.mut_jaeryo.saveaccount.data.Account

/**
 * Account Data를 접근하는 방법을 기술하는 interface
 */
interface AccountDataSource {

    interface LoadAccountsCallback {

        fun onAccountsLoaded(accounts: List<Account>)

        fun onDataNotAvailable()
    }

    interface GetAccountsCallback {

        fun onAccountsLoaded(account: Account)

        fun onDataNotAvailable()
    }

    fun getAccounts(callback : LoadAccountsCallback)

    fun getAccount(accountId : String, callback : GetAccountsCallback)

    fun saveAccount(account: Account)

    fun deleteAllAccounts()

    fun deleteAccount(accountId : String)
}