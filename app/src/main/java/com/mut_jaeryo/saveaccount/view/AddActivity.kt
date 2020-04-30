package com.mut_jaeryo.saveaccount.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.mut_jaeryo.saveaccount.R
import com.mut_jaeryo.saveaccount.data.Account

class AddActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SubCycle","onCreate")
        setContentView(R.layout.activity_add)

    }

    override fun onStart() {
        super.onStart()
        Log.d("SubCycle","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("SubCycle","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("SubCycle","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SubCycle","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SubCycle","onDestroy")
    }

}
