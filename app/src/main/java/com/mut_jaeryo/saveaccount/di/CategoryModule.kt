package com.mut_jaeryo.saveaccount.di

import androidx.fragment.app.Fragment
import com.mut_jaeryo.saveaccount.R
import com.mut_jaeryo.saveaccount.category.CategoryAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class CategoryModule {

    @Provides
    fun provideCategoryAdapter(fragment: Fragment) : CategoryAdapter =
        CategoryAdapter(fragment as? CategoryAdapter.OnCategorySelectedListener ,
                    fragment.resources.getStringArray(R.array.category_array).toList())
}