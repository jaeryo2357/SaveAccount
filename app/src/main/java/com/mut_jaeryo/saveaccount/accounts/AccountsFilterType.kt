package com.mut_jaeryo.saveaccount.accounts

import com.mut_jaeryo.saveaccount.R

enum class AccountsFilterType(val typeId : Int) {

    ALL_ACCOUNTS(R.string.category_all),

    GAME_ACCOUNTS(R.string.category_game),

    SNS_ACCOUNTS(R.string.category_sns),

    STUDY_ACCOUNTS(R.string.category_study),

    SECURITY_ACCOUNTS(R.string.category_security),

    OTHER_ACCOUNTS(R.string.category_other);

}