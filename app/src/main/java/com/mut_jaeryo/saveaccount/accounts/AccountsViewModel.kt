package com.mut_jaeryo.saveaccount.accounts

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mut_jaeryo.saveaccount.category.Category
import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.Result
import com.mut_jaeryo.saveaccount.data.Result.Success
import com.mut_jaeryo.saveaccount.data.source.AccountRepository

import kotlinx.coroutines.launch

class AccountsViewModel @ViewModelInject constructor(
    private val accountRepository: AccountRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _accountList: LiveData<List<Account>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            _dataLoading.value = true
            viewModelScope.launch {
                accountRepository.refreshAccounts()
            }
            _dataLoading.value = false
        }

        accountRepository.observeAccounts().distinctUntilChanged().switchMap { filterAccounts(it) }
    }
    val accountList = _accountList

    private val _dataLoading = MutableLiveData<Boolean>(false)
    val dataLoading = _dataLoading

    private val _dataEmpty = MutableLiveData<Boolean>(false)
    val dataEmpty = _dataEmpty

    private val _dataError = MutableLiveData<Boolean>(false)
    val dataError = _dataError

    init {
        setFiltering(getSavedFilterType())
        loadAccounts(true)
    }

    fun setFiltering(filterType: Category) {
        savedStateHandle.set(ACCOUNT_FILTER_SAVED_STATE_KEY, filterType)

        loadAccounts(false)
    }

    private fun filterAccounts(accounts: Result<List<Account>>): LiveData<List<Account>> {
        val result = MutableLiveData<List<Account>>()

        if (accounts is Success) {
            _dataError.value = false

            if (accounts.data.isEmpty()) {
                _dataEmpty.value = true
            } else {
                _dataEmpty.value = false
                viewModelScope.launch {
                    result.value = filterItem(accounts.data, getSavedFilterType())
                }
            }
        } else {
            _dataError.value = true
            result.value = emptyList()
        }

        return result
    }

    private suspend fun filterItem(
        accounts: List<Account>,
        filterType: Category = Category.ALL_ACCOUNTS
    ): List<Account> {
        val filteringAccounts = ArrayList<Account>()

        for (account in accounts) {
            when (filterType) {
                Category.ALL_ACCOUNTS -> filteringAccounts.add(account)

                else -> {
                    if (filterType.ordinal == account.categoryId) filteringAccounts.add(account)
                }
            }
        }

        return filteringAccounts
    }

    private fun getSavedFilterType(): Category {
        return savedStateHandle.get(ACCOUNT_FILTER_SAVED_STATE_KEY)
            ?: Category.ALL_ACCOUNTS
    }

    fun loadAccounts(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate;
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            accountRepository.deleteAccount(accountId = account.id)
        }
        loadAccounts(true)
    }

    fun refresh() {
        _forceUpdate.value = true;
    }
}

// Used to save the current filtering in SavedStateHandle.
const val ACCOUNT_FILTER_SAVED_STATE_KEY = "ACCOUNT_FILTER_SAVED_STATE_KEY"