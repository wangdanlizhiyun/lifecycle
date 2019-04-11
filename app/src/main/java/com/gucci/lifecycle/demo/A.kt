package com.gucci.lifecycle.demo

import android.util.Log
import com.gucci.lifecycle.LifecycleListener

/**
 * Created by 李志云 2019/4/11 22:46
 */
class A : LifecycleListener {
    override fun onStart() {

        Log.e("test", "${hashCode()} onStart")
    }

    override fun onStop() {
        Log.e("test", "${hashCode()} onStop")

    }

    override fun onDestory() {
        Log.e("test", "${hashCode()} onDestory")

    }

    override fun onResume() {
        Log.e("test", "${hashCode()} onResume")

    }

    override fun onCreate() {
        Log.e("test", "${hashCode()} onCreate")

    }

    override fun onPause() {
        Log.e("test", "${hashCode()} onPause")

    }


}
