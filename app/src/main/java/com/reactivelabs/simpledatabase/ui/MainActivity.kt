package com.reactivelabs.simpledatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reactivelabs.simpledatabase.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, MainFragment())
            .commit()
    }
}
