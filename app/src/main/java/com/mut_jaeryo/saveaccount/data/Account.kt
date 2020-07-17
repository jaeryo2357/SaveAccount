package com.mut_jaeryo.saveaccount.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * UUID 클래스를 이용하여 유일한 식별자를 생성
 */

@Entity(tableName = "accounts")
data class Account(
    @ColumnInfo(name = "site") val site : String = "",
    @ColumnInfo(name = "userId") var userId : String = "",
    @ColumnInfo(name = "userPwd") var userPwd : String = "",
    @PrimaryKey @ColumnInfo(name = "entryId") val id : String = UUID.randomUUID().toString()
)