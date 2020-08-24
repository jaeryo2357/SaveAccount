package com.mut_jaeryo.saveaccount.accounts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mut_jaeryo.saveaccount.R
import com.mut_jaeryo.saveaccount.addeditaccount.AddEditAccountActivity
import com.mut_jaeryo.saveaccount.addeditaccount.AddEditAccountActivity.Companion.ADD_REQUEST
import com.mut_jaeryo.saveaccount.addeditaccount.AddEditAccountActivity.Companion.EDIT_REQUEST
import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.source.AccountRepository
import com.mut_jaeryo.saveaccount.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_accounts.*

class AccountsActivity : AppCompatActivity(), AccountsContract.View{

    private val presenter : AccountsPresenter by lazy {
        AccountsPresenter( this,
            accountRepository = AccountRepository.Companion.getInstance(context = applicationContext),
            accountView = this@AccountsActivity
        )
    }


    internal var itemListener : AccountsAdapter.AccountItemListener = object : AccountsAdapter.AccountItemListener {
        override fun onAccountClick(clickedAccount: Account) {
            presenter.openAccountDetails(clickedAccount)
        }
    }

    private val listAdapter : AccountsAdapter = AccountsAdapter(this, ArrayList(0), itemListener)

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.result(requestCode, resultCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts)

        accounts_list.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(this@AccountsActivity)
        }

        add_Account.setOnClickListener { presenter.addNewAccount() }

    }

    override fun showAccountsDetailsUi(accountId: String) {
        val intent = Intent(this@AccountsActivity, AddEditAccountActivity::class.java).apply {
            putExtra("REQUEST_CODE", EDIT_REQUEST)
            putExtra("accountId", accountId)
        }
        startActivityForResult(intent , EDIT_REQUEST)
    }

    override fun showAddAccount() {
        val intent = Intent(this@AccountsActivity, AddEditAccountActivity::class.java).apply {
            putExtra("REQUEST_CODE", ADD_REQUEST)
        }
        startActivityForResult(intent , ADD_REQUEST)
    }

    override fun showAccounts(accounts: List<Account>) {
        listAdapter.accounts = accounts
        accounts_list_empty.visibility = View.INVISIBLE
        accounts_list.visibility = View.VISIBLE
    }

    override fun showNoAccounts() {
        accounts_list_empty.visibility = View.VISIBLE
        accounts_list.visibility = View.INVISIBLE
    }

    override fun showLoadingAccountsError() {
        showMessage(getString(R.string.loading_accounts_error))
    }

    override fun showSuccessFullySavedMessage() {
        showMessage(getString(R.string.loading_success))
    }

    override fun showLoadingIndicator(active: Boolean) {
        refresh_layout.apply {
            post { isRefreshing = active }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.refresh -> {
                presenter.loadAccounts(true)
            }
            R.id.filter -> {
                showFilterPopUpMenu()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.accounts_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun showFilterPopUpMenu() {
         PopupMenu(applicationContext, findViewById(R.id.filter)).apply {
             menuInflater.inflate(R.menu.filter_menu, menu)
             setOnMenuItemClickListener {
                 when(it.itemId) {
                     R.id.all -> presenter.filterType = AccountsFilterType.ALL_ACCOUNTS
                     R.id.sns -> presenter.filterType = AccountsFilterType.SNS_ACCOUNTS
                     R.id.study -> presenter.filterType = AccountsFilterType.STUDY_ACCOUNTS
                     R.id.security -> presenter.filterType = AccountsFilterType.SECURITY_ACCOUNTS
                     R.id.game -> presenter.filterType = AccountsFilterType.GAME_ACCOUNTS
                     else -> presenter.filterType = AccountsFilterType.OTHER_ACCOUNTS
                 }
                 presenter.loadAccounts(false)
                 true
             }
             show()
         }
    }

    private fun showMessage(message: String) {
        accounts_list.rootView.showSnackBar(message, Snackbar.LENGTH_SHORT)
    }
}
