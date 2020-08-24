package com.mut_jaeryo.saveaccount.category.utils

import android.content.Context
import com.mut_jaeryo.saveaccount.R

object CategoryUtil {

        fun getColorWithCategory(context: Context, type : String) : Int = when(type) {
            context.getString(R.string.category_security) -> R.color.colorSecurity

            context.getString(R.string.category_study) -> R.color.colorStudy

            context.getString(R.string.category_game) -> R.color.colorGame

            context.getString(R.string.category_other) -> R.color.colorOther

            else -> R.color.colorSNS
        }
}