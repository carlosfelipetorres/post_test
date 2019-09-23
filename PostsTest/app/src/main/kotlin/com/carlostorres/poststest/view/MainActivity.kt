package com.carlostorres.poststest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.carlostorres.poststest.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
