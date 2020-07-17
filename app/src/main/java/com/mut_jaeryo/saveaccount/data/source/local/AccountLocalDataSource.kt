package com.mut_jaeryo.saveaccount.data.source.local

import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.source.AccountDataSource
import com.mut_jaeryo.saveaccount.util.AppExecutor

/**
 * 현재 프로젝트에서 NetworkIO가 없지만 AppExecutor class는 Thread의 사용에 따라 DiskIo, NetworkIO, MainThread 3가지 백그라운드 작업으로 분리되었다.
 * 현재 class는 로컬의 Sqlite에서 데이터를 가져오는 작업으로 DiskIO를 주로 사용한다.
 */

class AccountLocalDataSource private constructor(
    val appExecutor: AppExecutor,
    val accountDao : AccountDao
) : AccountDataSource {

    override fun getAccounts(callback: AccountDataSource.LoadAccountsCallback) {
        appExecutor.diskIO.execute {
            val accounts = accountDao.getAccounts()
            appExecutor.mainThread.execute {
                if (accounts.isEmpty()) {
                    //테이블이 비어 있거나 데이터베이스가 존재하지 않을 때
                    callback.onDataNotAvailable()
                } else {
                    callback.onAccountsLoaded(accounts)
                }
            }
        }
    }

    override fun getAccount(accountId: String, callback: AccountDataSource.GetAccountsCallback) {
        appExecutor.diskIO.execute {
            val accountById = accountDao.getAccountById(accountId)
            appExecutor.mainThread.execute {
                if (accountById != null) {
                    callback.onAccountLoaded(accountById)
                } else {
                    //테이블이 비어 있거나 데이터베이스가 존재하지 않을 때
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun saveAccount(account: Account) {
        appExecutor.diskIO.execute { accountDao.insertAccount(account) }
    }

    override fun deleteAllAccounts() {
        appExecutor.diskIO.execute { accountDao.deleteAll() }
    }

    override fun deleteAccount(accountId: String) {
        appExecutor.diskIO.execute { accountDao.deleteAccountById(accountId) }
    }

    companion object {
        private var INSTANCE : AccountLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutor: AppExecutor, accountDao: AccountDao) : AccountLocalDataSource {
            if (INSTANCE == null) {
                synchronized(AccountLocalDataSource::class.java) { //여러 프로세스가 생성하지 않도록 lock을 걸어둔다.
                    INSTANCE = AccountLocalDataSource(appExecutor, accountDao)
                }
            }
            return INSTANCE!!
        }
    }
}