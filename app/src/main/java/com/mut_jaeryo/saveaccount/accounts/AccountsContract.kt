package com.mut_jaeryo.saveaccount.accounts

import com.mut_jaeryo.saveaccount.data.Account

interface AccountsContract {

    interface View {
        fun showAccountsDetailsUi(accountId : String)

        fun showAddAccount()
    }

    interface Presenter {
        fun openAccountDetails(account : Account)

        fun result(requestCode : Int, resultCode : Int)
    }
}