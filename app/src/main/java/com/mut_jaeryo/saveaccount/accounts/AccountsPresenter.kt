package com.mut_jaeryo.saveaccount.accounts

import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.source.AccountRepository

class AccountsPresenter(val accountRepository: AccountRepository,
                        val accountView : AccountsContract.View) : AccountsContract.Presenter {
    override fun openAccountDetails(account: Account) {
        TODO("Not yet implemented")
    }

    override fun result(requestCode: Int, resultCode: Int) {
        TODO("Not yet implemented")
    }

}