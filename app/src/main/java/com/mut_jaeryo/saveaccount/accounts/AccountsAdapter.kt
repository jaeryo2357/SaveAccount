package com.mut_jaeryo.saveaccount.accounts

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.saveaccount.R
import com.mut_jaeryo.saveaccount.category.utils.CategoryUtil
import com.mut_jaeryo.saveaccount.data.Account

class AccountsAdapter(val context : Context, accounts : List<Account>, val listener : AccountItemListener) : RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>() {

    var accounts : List<Account> = accounts
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_item, parent, false)
        return AccountsViewHolder(view)
    }

    override fun getItemCount() = accounts.size

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val account = accounts[position]
        val colorId = CategoryUtil.getColorWithCategory(context, account.category)
        val color = ContextCompat.getColor(context, colorId)

        holder.itemView.setOnClickListener { listener.onAccountClick(account) }

        holder.category?.apply {
            text = account.category
            setTextColor(color)
        }
        holder.colorView?.let {
            val drawable = it.drawable

            if (drawable is ShapeDrawable) {
                drawable.paint.color = color
            } else if (drawable is GradientDrawable) {
                drawable.setColor(color)
            }
        }
        holder.site?.text = account.site
    }

    interface AccountItemListener {
        fun onAccountClick(clickedAccount : Account)
    }

    class AccountsViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val colorView : ImageView = view.findViewById(R.id.account_category_color)
        val category : TextView? = view.findViewById(R.id.account_category)
        val site : TextView? = view.findViewById(R.id.account_title)
    }
}