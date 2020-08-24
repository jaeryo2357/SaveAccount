package com.mut_jaeryo.saveaccount.addeditaccount

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import com.mut_jaeryo.saveaccount.category.CategoryListDialogFragment
import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.source.AccountDataSource
import com.mut_jaeryo.saveaccount.data.source.AccountRepository
import java.lang.RuntimeException

class AddEditAccountPresenter(
    val accountId: String?,
    val accountRepository: AccountRepository,
    val accountView: AddEditContract.View
) : AddEditContract.Presenter, AccountDataSource.GetAccountsCallback, CategoryListDialogFragment.OnCategorySelectedListener{

    var category : String = "기타"

    override fun start() {
        if (accountId != null) {
            accountRepository.getAccount(accountId, this)
        } else {
            accountView.setCategory(category)
        }
    }

    override fun saveAccount(site: String, id: String, pwd: String) {

        if (accountId == null) {
            addAccount(site, id, pwd)
        } else {
            updateAccount(site, id, pwd)
        }
    }

    override fun addAccountView() {
        accountView.showAddAccountView()
    }

    override fun updateAccountView() {
        accountView.showEditAccountView()
    }

    override fun changeCategory() {
        accountView.showCategorySelectView()
    }

    override fun onAccountLoaded(account: Account) {
        accountView.setSite(account.site)
        accountView.setId(account.id)
        accountView.setPwd(account.userPwd)
        accountView.setCategory(account.category);
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

    override fun onSelected(category: String, position: Int) {
        this.category = category
        accountView.setCategory(category)
    }
}