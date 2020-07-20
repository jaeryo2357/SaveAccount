package com.mut_jaeryo.saveaccount.accounts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mut_jaeryo.saveaccount.R
import com.mut_jaeryo.saveaccount.insert.ui.InsertActivity
import kotlinx.android.synthetic.main.activity_main.*

class AccountsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        add_Account.setOnClickListener {
            val intent = Intent(this@AccountsActivity,
                InsertActivity::class.java)
            startActivity(intent)
        }
    }
}
