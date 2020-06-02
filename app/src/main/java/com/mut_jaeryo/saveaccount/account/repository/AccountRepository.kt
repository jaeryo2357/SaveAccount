package com.mut_jaeryo.saveaccount.account.repository

import androidx.lifecycle.LiveData
import com.mut_jaeryo.saveaccount.account.model.Account
import com.mut_jaeryo.saveaccount.account.model.AccountDao

//DAO의 접근을 Clean 추상화
class AccountRepository(private val accountDao : AccountDao) {

    val allAccounts: LiveData<List<Account>> = accountDao.getAlphabetizedWords()

    suspend fun insert(account: Account) {
        accountDao.insert(account)
    }
}