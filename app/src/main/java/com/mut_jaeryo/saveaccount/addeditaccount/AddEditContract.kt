package com.mut_jaeryo.saveaccount.addeditaccount

import com.mut_jaeryo.saveaccount.data.Account

interface AddEditContract {

    interface View {
        fun successSaveAccount()

        fun showAddAccountView()

        fun showEditAccountView()

        fun showEmptyAccountError()

        fun setSite(site: String)

        fun setId(id: String)

        fun setPwd(pwd: String)
        
        fun setCategory(category: String)
    }

    interface Presenter {

        fun start()

        fun saveAccount(site: String, id: String, pwd: String)
    }
}