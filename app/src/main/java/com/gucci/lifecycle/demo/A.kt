package com.gucci.lifecycle.demo

import android.util.Log
import com.gucci.lifecycle.LifecycleListener

/**
 * Created by 李志云 2019/4/11 22:46
 */
class A : LifecycleListener {
    override fun onStart() {

        Log.e("test", "A onStart")
    }

    override fun onStop() {
        Log.e("test", "A onStop")

    }

    override fun onDestory() {
        Log.e("test", "A onDestory")

    }

    override fun onResume() {
        Log.e("test", "A onResume")

    }

    override fun onCreate() {
        Log.e("test", "A onCreate")

    }

    override fun onPause() {
        Log.e("test", "A onPause")

    }


}
