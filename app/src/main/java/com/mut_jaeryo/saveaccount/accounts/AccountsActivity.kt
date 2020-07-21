package com.mut_jaeryo.saveaccount.accounts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.saveaccount.R
import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.data.source.AccountDataSource
import com.mut_jaeryo.saveaccount.data.source.AccountRepository
import com.mut_jaeryo.saveaccount.data.source.local.AccountLocalDataSource
import com.mut_jaeryo.saveaccount.insert.ui.InsertActivity
import kotlinx.android.synthetic.main.activity_accounts.*

class AccountsActivity : AppCompatActivity(), AccountsContract.View{

    private val presenter : AccountsPresenter by lazy {
        AccountsPresenter(
            accountRepository = AccountRepository.Companion.getInstance(context = applicationContext),
            accountView = this@AccountsActivity
        )
    }


    internal var itemListener : AccountsAdapter.AccountItemListener = object : AccountsAdapter.AccountItemListener {
        override fun onAccountClick(clickedAccount: Account) {
            presenter.openAccountDetails(clickedAccount)
        }

    }

    private val listAdapter : AccountsAdapter = AccountsAdapter(ArrayList(0), itemListener)

    override fun onResume() {
        super.onResume()
        //Presenter 데이터 로드
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

        add_Account.setOnClickListener { showAddAccount() }
    }

    override fun showAccountsDetailsUi(accountId: String) {

    }

    override fun showAddAccount() {
        val intent = Intent(this@AccountsActivity, InsertActivity::class.java)
        startActivityForResult(intent ,0)
    }

}
