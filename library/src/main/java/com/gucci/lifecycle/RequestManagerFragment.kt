package com.gucci.lifecycle

import android.app.Fragment
import android.os.Bundle
import android.util.EventLog
import com.gucci.lifecycle.ActivityFragmentLifecycle


/**
 * Created by 李志云 2019/4/11 16:49
 */
class RequestManagerFragment : Fragment() {

    val lifecycle = ActivityFragmentLifecycle()

    override fun onStart() {
        super.onStart()
        lifecycle.onStart()
    }

    override fun onStop() {
        super.onStop()
        lifecycle.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.onDestory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.onCreate()
    }

    override fun onPause() {
        super.onPause()
        lifecycle.onPause()
    }

    override fun onResume() {
        super.onResume()
        lifecycle.onResume()
    }
}