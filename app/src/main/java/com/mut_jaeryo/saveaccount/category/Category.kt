package com.mut_jaeryo.saveaccount.category

import com.mut_jaeryo.saveaccount.R

enum class Category(val typeId: Int) {

    GAME_ACCOUNTS(R.string.category_game),

    SNS_ACCOUNTS(R.string.category_sns),

    STUDY_ACCOUNTS(R.string.category_study),

    SECURITY_ACCOUNTS(R.string.category_security),

    OTHER_ACCOUNTS(R.string.category_other),

    ALL_ACCOUNTS(R.string.category_all);

    companion object {

        fun getFilter(filterOrdinal: Int) = when (filterOrdinal) {
            0 -> GAME_ACCOUNTS
            1 -> SNS_ACCOUNTS
            2 -> STUDY_ACCOUNTS
            3 -> SECURITY_ACCOUNTS
            4 -> OTHER_ACCOUNTS
            else -> ALL_ACCOUNTS
        }

        fun getColorWithFilterId(filterOrdinal: Int): Int = when (filterOrdinal) {
            0 -> R.color.colorGame
            1 -> R.color.colorSNS
            2 -> R.color.colorStudy
            3 -> R.color.colorSecurity
            else -> R.color.colorOther
        }
    }
}