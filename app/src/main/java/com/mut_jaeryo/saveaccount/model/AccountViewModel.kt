package com.mut_jaeryo.saveaccount.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mut_jaeryo.saveaccount.util.MyDatabaseHelper

class AccountViewModel : ViewModel() {

    private val _accountList = MutableLiveData<List<Account>>()
    val accountList : LiveData<List<Account>> = _accountList
}