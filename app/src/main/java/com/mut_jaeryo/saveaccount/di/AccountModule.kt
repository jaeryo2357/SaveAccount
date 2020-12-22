package com.mut_jaeryo.saveaccount.di

import android.app.Activity
import com.mut_jaeryo.saveaccount.accounts.AccountsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class AccountModule {

    @Provides
    fun provideAccountsAdapter(activity: Activity) : AccountsAdapter =
        AccountsAdapter(activity as? AccountsAdapter.AccountItemListener)
}