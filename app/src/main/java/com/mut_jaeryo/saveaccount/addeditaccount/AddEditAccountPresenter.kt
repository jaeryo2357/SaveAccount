package com.mut_jaeryo.saveaccount.addeditaccount

import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.source.AccountDataSource
import com.mut_jaeryo.saveaccount.data.source.AccountRepository
import java.lang.RuntimeException

class AddEditAccountPresenter(
    val accountId : String?,
    val accountRepository: AccountRepository,
    val accountView: AddEditContract.View
) : AddEditContract.Presenter, AccountDataSource.GetAccountsCallback{

    var category : String = ""

    override fun start() {
        if (accountId != null) {
            accountRepository.getAccount(accountId, this)
        }
    }

    override fun saveAccount(site: String, id: String, pwd: String) {

        if (accountId == null) {
            addAccount(site, id, pwd)
        } else {
            updateAccount(site, id, pwd)
        }
    }

    override fun onAccountLoaded(account: Account) {
        accountView.setSite(account.site)
        accountView.setId(account.id)
        accountView.setPwd(account.userPwd)
    }

    override fun onDataNotAvailable() {
        accountView.showEmptyAccountError()
    }

    private fun addAccount(site: String, id: String, pwd: String) {
        val account = Account(category, site, id, pwd)
        if (account.isEmpty()) {
            accountView.showEmptyAccountError()
        } else {
            accountRepository.saveAccount(account)
            accountView.successSaveAccount()
        }
    }

    private fun updateAccount(site: String, id: String, pwd: String) {
        if (accountId == null) {
            throw RuntimeException("updateAccount() was called but account is new")
        }

        val accountWithId = Account(category, site, id, pwd, accountId)
        accountRepository.saveAccount(accountWithId)
        accountView.successSaveAccount()
    }
}