package com.mut_jaeryo.saveaccount.accounts

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.saveaccount.data.Account

class AccountsAdapter(accounts : List<Account>, listener : AccountItemListener) : RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>() {

    var accounts : List<Account> = accounts
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = accounts.size

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


    interface AccountItemListener {
        fun onAccountClick(clickedAccount : Account)
    }

    class AccountsViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }
}