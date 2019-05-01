package com.gucci.lifecycle.demo

import android.util.Log
import com.gucci.lifecycle.LifecycleListener
import com.gucci.lifecycle.annotations.*

/**
 * Created by 李志云 2019/4/11 22:46
 */
class C : LifecycleListener {
    @OnStart fun onStart() {

        Log.e("test", "C onStart")
    }

    @OnStop fun onStop() {
        Log.e("test", "C onStop")

    }

    @OnDestory fun onDestory() {
        Log.e("test", "C onDestory")

    }

    @OnResume fun onResume() {
        Log.e("test", "C onResume")

    }

    @OnCreate
    fun c() {
        Log.e("test", "C onCreate")

    }

    @OnPause fun onPause() {
        Log.e("test", "C onPause")

    }


}
