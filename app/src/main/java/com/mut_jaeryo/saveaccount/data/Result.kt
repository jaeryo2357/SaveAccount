package com.mut_jaeryo.saveaccount.data

import java.lang.Exception

sealed class Result<out R> {
    data class Success<out T> (val data: T) : Result<T>()

    data class Error(val exception: Exception) : Result<Nothing>()
}