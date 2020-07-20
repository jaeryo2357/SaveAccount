package com.mut_jaeryo.saveaccount.account.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mut_jaeryo.saveaccount.data.Account

class mainViewModel : ViewModel() {

    private val _accountList = MutableLiveData<List<Account>>()
    val accountList : LiveData<List<Account>> = _accountList
}