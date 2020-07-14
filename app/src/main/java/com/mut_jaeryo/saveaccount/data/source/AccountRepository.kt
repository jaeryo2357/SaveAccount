package com.mut_jaeryo.saveaccount.data.source

import com.mut_jaeryo.saveaccount.data.Account

class AccountRepository(
  val dataSource: AccountDataSource
  ) : AccountDataSource{


    override fun getAccounts(callback: AccountDataSource.LoadAccountsCallback) {
        TODO("Not yet implemented")
    }

    override fun getAccount(accountId: String, callback: AccountDataSource.GetAccountsCallback) {
        TODO("Not yet implemented")
    }

    override fun saveAccount(account: Account) {
        TODO("Not yet implemented")
    }

    override fun deleteAllAccounts() {
        TODO("Not yet implemented")
    }

    override fun deleteAccount(account: Account) {
        TODO("Not yet implemented")
    }


    companion object {
        private var Instance : AccountRepository? = null

        @JvmStatic fun getInstance(
            dataSource: AccountDataSource
        ) : AccountRepository {
            return Instance ?: AccountRepository(dataSource).apply {
                Instance = this
            }
        }

        @JvmStatic fun destroyInstance() {
            Instance = null
        }
    }
}