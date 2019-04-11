package com.gucci.lifecycle.demo

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gucci.lifecycle.watch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        A().watch(this)
        A().watch(this)
        A().watch(this)
        A().watch(this)
    }
}
