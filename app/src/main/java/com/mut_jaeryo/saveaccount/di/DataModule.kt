package com.mut_jaeryo.saveaccount.di

import android.content.Context
import com.mut_jaeryo.saveaccount.data.source.AccountDataSource
import com.mut_jaeryo.saveaccount.data.source.AccountRepository
import com.mut_jaeryo.saveaccount.data.source.AccountRepositoryImpl
import com.mut_jaeryo.saveaccount.data.source.local.AccountDao
import com.mut_jaeryo.saveaccount.data.source.local.AccountDataBase
import com.mut_jaeryo.saveaccount.data.source.local.LocalAccountDataSource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
class DataModule {

    @Provides
    fun provideAccountRepository(dataSource: AccountDataSource) : AccountRepository =
        AccountRepositoryImpl(dataSource)

    @Provides
    fun provideAccountDataSource(dao: AccountDao) : AccountDataSource =
        LocalAccountDataSource(dao)

    @Provides
    fun provideAccountDao(@ApplicationContext context: Context) : AccountDao =
        AccountDataBase.getInstance(context).accountDao()

}