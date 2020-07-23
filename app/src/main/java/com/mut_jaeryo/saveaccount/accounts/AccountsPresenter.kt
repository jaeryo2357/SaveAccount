package com.mut_jaeryo.saveaccount.accounts

import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.source.AccountDataSource
import com.mut_jaeryo.saveaccount.data.source.AccountRepository

class AccountsPresenter(
    val accountRepository: AccountRepository,
    val accountView: AccountsContract.View
) : AccountsContract.Presenter {

    private var firstLoad = true;

    override fun start() {
        loadAccounts()
    }

    override fun loadAccounts() {
        if (firstLoad) {
            accountView.showLoadingIndicator(true)

            accountRepository.getAccounts(object : AccountDataSource.LoadAccountsCallback {
                override fun onAccountsLoaded(accounts: List<Account>) {
                    processAccounts(accounts)
                }

                override fun onDataNotAvailable() {
                    accountView.showLoadingAccountsError()
                }

            })

            accountView.showLoadingIndicator(false)
            firstLoad = false
        }
    }

    private fun processAccounts(accounts: List<Account>) {
        if (accounts.isEmpty()) {
            accountView.showNoAccounts()
        } else {
            accountView.showAccounts(accounts)
        }
    }

    override fun openAccountDetails(account: Account) {
        accountView.showAccountsDetailsUi(account.id)
    }

    override fun addNewAccount() {
        accountView.showAddAccount()
    }

    override fun result(requestCode: Int, resultCode: Int) {
        TODO("Not yet implemented")
    }

}