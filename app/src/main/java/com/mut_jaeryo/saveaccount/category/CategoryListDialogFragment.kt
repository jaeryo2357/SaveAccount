package com.mut_jaeryo.saveaccount.category

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mut_jaeryo.saveaccount.R

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    CategoryListDialogFragment.newInstance().show(supportFragmentManager, "dialog")
 * </pre>
 */
class CategoryListDialogFragment : BottomSheetDialogFragment() {

    private var selectedListener : OnCategorySelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.findViewById<RecyclerView>(R.id.list)?.layoutManager =
            LinearLayoutManager(context)
        activity?.findViewById<RecyclerView>(R.id.list)?.adapter = CategoryAdapter(context!!)
    }

    public fun setSelectedListener(listener: OnCategorySelectedListener) {
        this.selectedListener = listener;
    }

    private inner class ViewHolder internal constructor(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.fragment_item_list_dialog_list_dialog_item,
            parent,
            false
        )
    ) {

        internal val text: TextView = itemView.findViewById(R.id.text_category)

        internal val color: ImageView = itemView.findViewById(R.id.imageView_category)
    }

    private inner class CategoryAdapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {

        private val category : Array<String> by lazy {
            context.resources.getStringArray(R.array.category_array);
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = category[position]

            holder.itemView.setOnClickListener {
                selectedListener?.onSelected(category[position], position)
            }
        }

        override fun getItemCount(): Int {
            return category.size
        }
    }

    interface OnCategorySelectedListener {
        fun onSelected(category: String, position : Int)
    }

    companion object {

        fun newInstance(itemCount: Int): CategoryListDialogFragment =
            CategoryListDialogFragment()
    }
}