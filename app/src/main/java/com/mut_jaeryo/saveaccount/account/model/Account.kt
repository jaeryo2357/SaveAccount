package com.mut_jaeryo.saveaccount.account.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_table")
data class Account(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "site") val site : String,
    @ColumnInfo(name = "user_id") var userId : String,
    @ColumnInfo(name = "user_pwd") var userPwd : String
)