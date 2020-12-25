package com.mut_jaeryo.saveaccount.accounts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mut_jaeryo.saveaccount.R
import com.mut_jaeryo.saveaccount.addeditaccount.AddEditAccountActivity
import com.mut_jaeryo.saveaccount.category.Category
import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.databinding.ActivityAccountsBinding
import com.mut_jaeryo.saveaccount.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountsActivity : AppCompatActivity(), AccountsAdapter.AccountItemListener {

    private val viewModel: AccountsViewModel by viewModels<AccountsViewModel>()

    private lateinit var accountBinding: ActivityAccountsBinding

    @Inject
    lateinit var accountsAdapter: AccountsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountBinding = DataBindingUtil.setContentView(this, R.layout.activity_accounts)
        accountBinding.apply {
            vm = viewModel
            lifecycleOwner = this@AccountsActivity
        }

        setupList()
        setupFAB()
        setupErrorHandle()
    }

    private fun setupErrorHandle() {
        viewModel.dataError.observe(this, Observer {
            if (it) {
                showLoadingAccountsError()
            }
        })
    }

    private fun setupList() {
        accountBinding.accountsList.adapter = accountsAdapter
    }

    private fun setupFAB() {
        accountBinding.addAccountFab.setOnClickListener {
            showAddAccount()
        }
    }

    private fun showLoadingAccountsError() {
        showMessage(getString(R.string.loading_accounts_error))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                viewModel.refresh()
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

    private fun showFilterPopUpMenu() {
        PopupMenu(applicationContext, findViewById(R.id.filter)).apply {
            menuInflater.inflate(R.menu.filter_menu, menu)
            setOnMenuItemClickListener {
                val filterType = when (it.itemId) {
                    R.id.all -> Category.ALL_ACCOUNTS
                    R.id.sns -> Category.SNS_ACCOUNTS
                    R.id.study -> Category.STUDY_ACCOUNTS
                    R.id.security -> Category.SECURITY_ACCOUNTS
                    R.id.game -> Category.GAME_ACCOUNTS
                    else -> Category.OTHER_ACCOUNTS
                }
                viewModel.setFiltering(filterType)
                true
            }
            show()
        }
    }

    private fun showMessage(message: String) {
        accountBinding.root.showSnackBar(message, Snackbar.LENGTH_SHORT)
    }

    private fun showAddAccount() {
        val intent = Intent(this@AccountsActivity, AddEditAccountActivity::class.java)
        startActivity(intent)
    }

    private fun showAccountsDetailsUi(accountId: Int) {
        val intent = Intent(this@AccountsActivity, AddEditAccountActivity::class.java).apply {
            putExtra("accountId", accountId)
        }
        startActivity(intent)
    }

    override fun onAccountClick(clickedAccount: Account?) {
        clickedAccount?.let {
            showAccountsDetailsUi(it.id)
        }
    }

    override fun onDeleteAccountClick(clickedAccount: Account?) {
        val builder = AlertDialog.Builder(this@AccountsActivity).apply {
            setTitle(getString(R.string.delete_dialog_title)).setMessage(getString(R.string.delete_dialog_content))
            setPositiveButton(getString(R.string.delete_dialog_action)) { _, _ ->
                clickedAccount?.let {
                    viewModel.deleteAccount(clickedAccount)
                    
                }
            }
            setNegativeButton(getString(R.string.delete_dialog_cancel)) { _, _ ->
                //..
            }
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}
