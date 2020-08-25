package com.mut_jaeryo.saveaccount.accounts

import android.app.Activity.RESULT_OK
import android.content.Context
import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.source.AccountDataSource
import com.mut_jaeryo.saveaccount.data.source.AccountRepository

class AccountsPresenter(
    val context : Context,
    val accountRepository: AccountRepository,
    val accountView: AccountsContract.View
) : AccountsContract.Presenter {

    var filterType : AccountsFilterType = AccountsFilterType.ALL_ACCOUNTS

    private var firstLoad = true;

    override fun start() {
        loadAccounts(false)
    }

    override fun loadAccounts(forceUpdate: Boolean) {
        if (firstLoad) {
            accountView.showLoadingIndicator(true)

            if (forceUpdate) accountRepository.refreshAccounts()

            accountRepository.getAccounts(object : AccountDataSource.LoadAccountsCallback {
                override fun onAccountsLoaded(accounts: List<Account>) {
                    val filteringAccounts = ArrayList<Account>()

                    for(account in accounts) {
                        when (filterType) {
                            AccountsFilterType.ALL_ACCOUNTS -> filteringAccounts.add(account)

                            else -> {
                                val categoryFilter : String = context.getString(filterType.typeId)
                                if (categoryFilter == account.category) filteringAccounts.add(account)
                            }
                        }
                    }
                    processAccounts(filteringAccounts)
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
        if (resultCode == RESULT_OK) {
            firstLoad = true
            loadAccounts(true)
        }
    }

}