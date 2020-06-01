package com.mut_jaeryo.saveaccount.account.db

import android.content.Context

class AccountDB {

    companion object {
        @Volatile private var db : Database? = null

        @JvmStatic fun getInstance(context : Context): Database =
            db ?: synchronized(this) {
                db ?: Database(context).also {
                    db = it
                }
            }
    }
}