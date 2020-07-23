package com.mut_jaeryo.saveaccount.accounts

import com.mut_jaeryo.saveaccount.data.Account

interface AccountsContract {

    interface View {
        fun showAccountsDetailsUi(accountId : String)

        fun showAddAccount()

        fun showAccounts(accounts : List<Account>)

        fun showNoAccounts()

        fun showLoadingAccountsError()

        fun showSuccessFullySavedMessage()

        fun showLoadingIndicator(active : Boolean)
    }

    interface Presenter {
        fun start()

        fun loadAccounts()

        fun openAccountDetails(account : Account)

        fun addNewAccount()

        fun result(requestCode : Int, resultCode : Int)
    }
}