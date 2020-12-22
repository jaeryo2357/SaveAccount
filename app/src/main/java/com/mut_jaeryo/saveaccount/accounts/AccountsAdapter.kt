package com.mut_jaeryo.saveaccount.accounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.saveaccount.data.Account
import com.mut_jaeryo.saveaccount.databinding.AccountItemBinding
import javax.inject.Inject

class AccountsAdapter @Inject constructor(private val listener: AccountItemListener?) :
    ListAdapter<Account, AccountsAdapter.AccountViewHolder>(AccountDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        return AccountViewHolder.from(parent).apply {
            itemView.setOnClickListener {
                val account = getItem(adapterPosition)
                    listener?.onAccountClick(account)
            }

            itemView.setOnLongClickListener {
                val account = getItem(adapterPosition)
                listener?.onDeleteAccountClick(account)
                true
            }
        }
    }


    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class AccountViewHolder(private val binding: AccountItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Account) {
            binding.account = account
            //즉시 실행
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AccountViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AccountItemBinding.inflate(layoutInflater, parent, false)
                return AccountViewHolder(binding)
            }
        }
    }

    interface AccountItemListener {
        fun onAccountClick(clickedAccount: Account?)

        fun onDeleteAccountClick(clickedAccount: Account?)
    }
}

object AccountDiffCallback : DiffUtil.ItemCallback<Account>() {
    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem == newItem
    }
}