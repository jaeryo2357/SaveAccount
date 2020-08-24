package com.mut_jaeryo.saveaccount.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}