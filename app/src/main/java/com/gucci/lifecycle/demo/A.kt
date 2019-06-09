package com.gucci.lifecycle.demo

import android.util.Log
import com.gucci.lifecycle.LifecycleListener
import com.gucci.lifecycle.annotations.*

class A : LifecycleListener {
    val b = B()

    @OnStart
    fun onStart() {
        Log.e("test", "A onStart")
    }

    @OnStop
    fun onStop() {
        Log.e("test", "A onStop")

    }

    @OnDestory
    fun onDestory() {
        Log.e("test", "A onDestory")

    }

    @OnResume
    fun onResume() {
        Log.e("test", "A onResume")

    }

    @OnCreate
    fun c() {
        Log.e("test", "A onCreate")

    }

    @OnPause
    fun onPause() {
        Log.e("test", "A onPause")

    }


}
