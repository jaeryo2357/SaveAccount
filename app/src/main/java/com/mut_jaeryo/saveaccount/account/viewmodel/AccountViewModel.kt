package com.mut_jaeryo.saveaccount.account.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mut_jaeryo.saveaccount.account.db.AccountDB
import com.mut_jaeryo.saveaccount.account.model.Account
import com.mut_jaeryo.saveaccount.account.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AccountRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Account>>

    init {
        val accountsDao = AccountDB.getDatabase(application).accountDao()
        repository = AccountRepository(accountsDao)
        allWords = repository.allAccounts
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(account)
    }
}