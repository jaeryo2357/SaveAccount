package com.mut_jaeryo.saveaccount.accounts

import com.mut_jaeryo.saveaccount.R

enum class AccountsFilterType(val type : String) {

    ALL_ACCOUNTS("모두"),

    GAME_ACCOUNTS("게임"),

    SNS_ACCOUNTS("SNS"),

    STUDY_ACCOUNTS("공부"),

    SECURITY_ACCOUNTS("보안");

    companion object {
        fun getColor(type : String) : Int = when(type) {
            "보안" -> R.color.colorSecurity

            "스터디" -> R.color.colorStudy

            "게임" -> R.color.colorGame

            else -> R.color.colorSNS
        }
    }
}