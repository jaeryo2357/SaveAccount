package com.mut_jaeryo.saveaccount.accounts

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mut_jaeryo.saveaccount.data.Account

@BindingAdapter("app:accountItems")
fun setItems(recyclerView: RecyclerView, items: List<Account>?) {
    val adapter = recyclerView.adapter as AccountsAdapter

    items?.let { adapter.submitList(items) }
    adapter.notifyDataSetChanged()
}


@BindingAdapter("app:refresh")
fun setRefresh(
    refreshLayout: SwipeRefreshLayout,
    refresh: Boolean
) {
    refreshLayout.isRefreshing = refresh
}

@BindingAdapter("app:visible")
fun setVisible(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}