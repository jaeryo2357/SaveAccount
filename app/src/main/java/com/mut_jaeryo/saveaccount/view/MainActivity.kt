package com.mut_jaeryo.saveaccount.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mut_jaeryo.saveaccount.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        add_Account.setOnClickListener {
            val intent = Intent(this@MainActivity,
                AddActivity::class.java)
            startActivity(intent)
        }
    }
}
