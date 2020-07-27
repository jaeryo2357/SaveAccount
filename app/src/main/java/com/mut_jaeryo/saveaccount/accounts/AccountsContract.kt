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

        fun showFilterPopUpMenu()
    }

    interface Presenter {
        fun start()

        /**
          @param forceUpdate true 일 경우 캐시된 메모리 지우고 다시 가져옴
         */
        fun loadAccounts(forceUpdate : Boolean)

        fun openAccountDetails(account : Account)

        fun addNewAccount()

        fun result(requestCode : Int, resultCode : Int)
    }
}