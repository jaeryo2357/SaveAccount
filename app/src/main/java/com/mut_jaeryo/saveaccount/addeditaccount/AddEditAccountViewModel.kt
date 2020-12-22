package com.mut_jaeryo.saveaccount.addeditaccount

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mut_jaeryo.saveaccount.category.Category
import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.Result
import com.mut_jaeryo.saveaccount.data.source.AccountRepository
import kotlinx.coroutines.launch

class AddEditAccountViewModel @ViewModelInject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _accountCategoryId = MutableLiveData<Int>(0)
    val accountCategoryId = _accountCategoryId

    private val _accountSiteName = MutableLiveData<String>()
    val accountSiteName = _accountSiteName

    private val _userId = MutableLiveData<String>()
    val userId = _userId

    private val _userPassword = MutableLiveData<String>()
    val userPassword = _userPassword

    private val _dataLoading = MutableLiveData<Boolean>(false)
    val dataLoading = _dataLoading

    private val _dataEmpty = MutableLiveData<Boolean>()
    val dataEmpty = _dataEmpty

    private val _dataSaved = MutableLiveData<Boolean>()
    val dataSaved = _dataSaved

    private var accountId: Int? = null

    private var isDataLoaded: Boolean = false

    private var isNewAccount: Boolean = false

    fun start(accountId: Int?) {
        this.accountId = accountId

        if (accountId == null) {
            isNewAccount = true
            return
        }

        if (isDataLoaded) {
            return
        }

        isNewAccount = false
        _dataLoading.value = true

        viewModelScope.launch {
            accountRepository.getAccount(accountId).let { result ->
                if (result is Result.Success) {
                    onAccountLoaded(result.data)
                } else {
                    onDataNotAvailable()
                }
            }
        }
    }

    fun changeCategory(categoryId: Int) {
        _accountCategoryId.value = categoryId
    }

    fun saveAccount() {
        val categoryId = accountCategoryId.value
        val siteName = accountSiteName.value
        val userId = userId.value
        val userPwd= userPassword.value

        if (categoryId == null || siteName == null ||
                userId == null || userPwd == null) {
            _dataEmpty.value = true
            return
        }

        var account = Account(
            categoryId = categoryId,
            site = siteName,
            userId = userId,
            userPwd = userPwd)

        if (account.isEmpty()) {
            _dataEmpty.value = true
            return
        }

        if (isNewAccount && accountId == null) {
            viewModelScope.launch {
                accountRepository.saveAccount(account)
                _dataSaved.value = true
            }
        } else {
            accountId?.let {
                account = Account(
                    categoryId = categoryId,
                    site = siteName,
                    userId = userId,
                    userPwd = userPwd,
                    id = it)

                viewModelScope.launch {
                    accountRepository.saveAccount(account)
                    _dataSaved.value = true
                }
            }
        }
    }

    private fun onAccountLoaded(account: Account) {
        _accountCategoryId.value = Category.getFilter(account.categoryId).typeId
        _accountSiteName.value = account.site
        _userId.value = account.userId
        _userPassword.value = account.userPwd
        _dataLoading.value = false
        isDataLoaded = true
    }

    private fun onDataNotAvailable() {
        _dataLoading.value = false
    }
}