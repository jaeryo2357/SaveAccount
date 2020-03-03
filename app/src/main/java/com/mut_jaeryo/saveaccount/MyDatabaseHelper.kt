package com.mut_jaeryo.saveaccount

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null, VERSION_CODE) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME ($COLURM_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COLURM_SITE TEXT, $COLURM_ACCOUNT_ID TEXT, $COLURM_ACCOUNT_PASSWORD TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $DATABASE_NAME")
        onCreate(db)
    }

    companion object
    {
        private const val DATABASE_NAME = "Account.db"
        private const val VERSION_CODE = 1

        private val TABLE_NAME = "my_account"
        private val COLURM_ID = "_id"
        private val COLURM_SITE = "Site"
        private val COLURM_ACCOUNT_ID = "account_id"
        private val COLURM_ACCOUNT_PASSWORD = "account_password"
    }
}