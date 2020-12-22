package com.mut_jaeryo.saveaccount.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.mut_jaeryo.saveaccount.databinding.ItemFragmentCategoryBinding
import javax.inject.Inject

class CategoryAdapter @Inject constructor(
    private val listener: OnCategorySelectedListener?,
    private val categoryList: List<String>? = null
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        categoryList?.let { holder.bind(position) }
    }

    override fun getItemCount(): Int {
        return categoryList?.size ?: 0
    }

    class CategoryViewHolder(val binding: ItemFragmentCategoryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryId: Int) {
            binding.categoryId = categoryId
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup,
                     listener: OnCategorySelectedListener?
            ) : CategoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFragmentCategoryBinding.inflate(layoutInflater, parent, false).apply {
                    root.setOnClickListener {
                        listener?.onSelected(this.categoryId ?: 0)
                    }
                }

                return CategoryViewHolder(binding)
            }
        }
    }

    interface OnCategorySelectedListener {
        fun onSelected(categoryId: Int)
    }
}