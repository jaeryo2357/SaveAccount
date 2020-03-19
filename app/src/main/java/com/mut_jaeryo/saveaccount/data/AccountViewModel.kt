package com.mut_jaeryo.saveaccount.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {

    private val _accountList = MutableLiveData<List<Account>>()
    val accountList = _accountList



}