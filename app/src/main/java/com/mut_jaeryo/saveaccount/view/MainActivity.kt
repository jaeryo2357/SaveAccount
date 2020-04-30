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
        Log.d("MainCycle","onCreate")



        add_Account.setOnClickListener {
            val intent = Intent(this@MainActivity,
                AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainCycle","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainCycle","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainCycle","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainCycle","onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainCycle","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainCycle","onRestart")
    }

}
