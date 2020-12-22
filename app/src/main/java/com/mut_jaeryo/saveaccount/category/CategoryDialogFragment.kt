package com.mut_jaeryo.saveaccount.category

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mut_jaeryo.saveaccount.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryDialogFragment (
    private val listener: CategoryAdapter.OnCategorySelectedListener? = null
) : BottomSheetDialogFragment(), CategoryAdapter.OnCategorySelectedListener {

    private lateinit var binding: FragmentCategoryBinding

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        setupCategoryList()
    }

    private fun setupCategoryList() {
        binding.list.adapter = categoryAdapter
    }

    override fun onSelected(categoryId: Int) {
        listener?.onSelected(categoryId)
        this.dismiss()
    }
}