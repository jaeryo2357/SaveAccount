package com.mut_jaeryo.saveaccount.addeditaccount

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar

import com.mut_jaeryo.saveaccount.R
import com.mut_jaeryo.saveaccount.category.CategoryListDialogFragment
import com.mut_jaeryo.saveaccount.data.source.AccountRepository
import com.mut_jaeryo.saveaccount.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_addedit.*


/**
 * Edit(Detail)과 Add 기능을 수행하는 Activity
 */

class AddEditAccountActivity : AppCompatActivity(), AddEditContract.View {

    private val presenter : AddEditAccountPresenter by lazy {
        AddEditAccountPresenter(
            intent.getStringExtra("accountId"),
            AccountRepository.getInstance(this),
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addedit)

        if (intent.getIntExtra("REQUEST_CODE", -1) == ADD_REQUEST) {
            presenter.addAccountView()
        } else {
            presenter.updateAccountView()
        }

        add_edit_account_btn.setOnClickListener {
            presenter.saveAccount(
                site = editText_site.text.toString(),
                id = editText_Id.text.toString(),
                pwd = editText_Pwd.text.toString()
            )
        }

        category_card.setOnClickListener { presenter.changeCategory() }
    }

    override fun successSaveAccount() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showAddAccountView() {
        add_edit_account_btn.text = getString(R.string.add_btn)
    }

    override fun showEditAccountView() {
        add_edit_account_btn.text = getString(R.string.edit_btn)
    }

    override fun showEmptyAccountError() {
        editText_site.showSnackBar(getString(R.string.empty_account_error), Snackbar.LENGTH_SHORT)
    }

    override fun setSite(site: String) {
        editText_site.setText(site)
    }

    override fun setId(id: String) {
        editText_Id.setText(id)
    }

    override fun setPwd(pwd: String) {
        editText_Pwd.setText(pwd)
    }

    override fun setCategory(category: String) {
        selected_category.text = category
    }

    override fun showCategorySelectView() {
        val modalDialog = CategoryListDialogFragment.newInstance()
        modalDialog.run {
            setSelectedListener(presenter)
            show(supportFragmentManager, "dialog")
        }
    }

    companion object {
        const val ADD_REQUEST : Int = 100
        const val EDIT_REQUEST : Int = 200
    }
}
