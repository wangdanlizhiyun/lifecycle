package com.gucci.lifecycle.demo

import android.util.Log
import com.gucci.lifecycle.LifecycleListener
import com.gucci.lifecycle.annotations.*

/**
 * Created by 李志云 2019/4/11 22:46
 */
class B : LifecycleListener {
    @OnStart fun onStart() {

        Log.e("test", "B onStart")
    }

    @OnStop fun onStop() {
        Log.e("test", "B onStop")

    }

    @OnDestory fun onDestory() {
        Log.e("test", "B onDestory")

    }

    @OnResume fun onResume() {
        Log.e("test", "B onResume")

    }

    @OnCreate
    fun c() {
        Log.e("test", "B onCreate")

    }

    @OnPause fun onPause() {
        Log.e("test", "B onPause")

    }


}
