package com.mut_jaeryo.saveaccount.addeditaccount

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

import com.mut_jaeryo.saveaccount.R
import com.mut_jaeryo.saveaccount.category.CategoryAdapter
import com.mut_jaeryo.saveaccount.category.CategoryDialogFragment
import com.mut_jaeryo.saveaccount.databinding.ActivityAddeditBinding
import com.mut_jaeryo.saveaccount.utils.showSnackBar

import dagger.hilt.android.AndroidEntryPoint


/**
 * Edit(Detail)과 Add 기능을 수행하는 Activity
 */

@AndroidEntryPoint
class AddEditAccountActivity : AppCompatActivity(), CategoryAdapter.OnCategorySelectedListener {

    private val viewModel by viewModels<AddEditAccountViewModel>()

    private lateinit var binding: ActivityAddeditBinding

    private var categoryDialog : CategoryDialogFragment? = null

    private var accountId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addedit)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@AddEditAccountActivity
        }

        val accountId: Int = intent.getIntExtra("accountId", -1)
        if (accountId != -1) this.accountId = accountId

        viewModel.start(this.accountId)

        setupObserve()
        setupCategory()
    }

    private fun setupObserve() {
        viewModel.dataSaved.observe(this, Observer {
            if (it) {
                showAccountSavedMessage()
            }
        })

        viewModel.dataEmpty.observe(this, Observer {
            if (it) {
                showEmptyAccountErrorMessage()
            }
        })
    }

    private fun setupCategory() {
        binding.categoryCard.setOnClickListener {
            showCategorySelectView()
        }
    }

    private fun showAccountSavedMessage() {
        binding.root.showSnackBar(getString(R.string.account_save_success), Snackbar.LENGTH_SHORT)
    }

    private fun showEmptyAccountErrorMessage() {
        binding.root.showSnackBar(getString(R.string.empty_account_error), Snackbar.LENGTH_SHORT)
    }

    private fun showCategorySelectView() {
        if (categoryDialog == null) {
            categoryDialog = CategoryDialogFragment(this)
        }
        categoryDialog?.show(supportFragmentManager, "dialog")
    }

    override fun onSelected(categoryId: Int) {
        viewModel.changeCategory(categoryId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return true
    }
}
